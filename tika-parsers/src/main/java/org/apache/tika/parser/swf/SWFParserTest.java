/*
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

import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

/**
 * Test case for parsing swf files.
 */
public class SWFParserTest extends TestCase {

	public void testSWFParsing() throws Exception {
		Parser parser = new AutoDetectParser(); // Should auto-detect!
		ContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();

		InputStream stream = SWFParserTest.class
				.getResourceAsStream("/test-documents/test.swf");
		try {
			parser.parse(stream, handler, metadata, new ParseContext());
		} finally {
			stream.close();
		}

		String content = handler.toString();
		assertTrue(content.contains("Mix."));
		assertTrue(content.contains("Edit."));
		assertTrue(content.contains("Master."));
		assertTrue(content.contains("Compose."));
		assertTrue(content.contains("Animate."));
		assertTrue(content.contains("With a single suite of powerful tools"));
		assertTrue(content.contains("that work together as one."));
		assertTrue(content
				.contains("World-class video and audio tools that bring"));
		assertTrue(content
				.contains("new power and efficiency to your film, video,"));
		assertTrue(content.contains("DVD, and web workflows."));
		assertTrue(content.contains("Learn more."));
	}
}