/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tika.parser.swf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.anotherbigidea.flash.readers.SWFReader;
import com.anotherbigidea.flash.readers.TagParser;
import com.anotherbigidea.io.InStream;

/**
 * Parser for Flash SWF files. Based on JavaSWF and similar to A. Bialecki's
 * implementation for Nutch except that we do not handle text from the actions
 * or structured URLS. URLs can be obtained from the text extracted using
 * ParserPostProcessor
 * 
 * @author Julien Nioche
 */
public class SWFParser implements Parser {

	/**
	 * @deprecated This method will be removed in Apache Tika 1.0.
	 */
	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata) throws IOException, SAXException, TikaException {
		parse(stream, handler, metadata, new ParseContext());
	}

	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata, ParseContext context) throws IOException,
			SAXException, TikaException {
		try {
			ExtractText extractor = new ExtractText();

			// TagParser implements SWFTags and drives a SWFTagTypes interface
			TagParser parser = new TagParser(extractor);

			// SWFReader reads an input file and drives a SWFTags interface
			SWFReader reader = new SWFReader(parser, new InStream(stream));

			// read the input SWF file and pass it through the interface
			// pipeline
			reader.readFile();
			String text = extractor.getText();
			// we leave the extraction of links from the text to the
			// ParserPostProcessor

			XHTMLContentHandler xhtml = new XHTMLContentHandler(handler,
					metadata);
			xhtml.startDocument();
			xhtml.element("p", extractor.getText());
			xhtml.endDocument();
		} catch (Exception e) {
			throw new TikaException("Error parsing an SWF document", e);
		}
	}

	private static final Set<MediaType> SUPPORTED_TYPES =
	        Collections.singleton(MediaType.application("x-shockwave-flash"));

	    public Set<MediaType> getSupportedTypes(ParseContext context) {
	        return SUPPORTED_TYPES;
	    }
}