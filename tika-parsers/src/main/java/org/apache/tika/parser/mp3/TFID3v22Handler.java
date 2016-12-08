package org.apache.tika.parser.mp3;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.mp3.TFID3Tags.TFID3Comment;
import org.apache.tika.parser.mp3.TFID3v2Frame.RawTag;
import org.apache.tika.parser.mp3.TFID3v2Frame.RawTagIterator;
import org.xml.sax.SAXException;

/**
 * This is used to parse ID3 Version 2.2 Tag information from an MP3 file,
 * if available.
 *
 * @see <a href="http://id3lib.sourceforge.net/id3/id3v2-00.txt">MP3 ID3 Version 2.2 specification</a>
 */
public class TFID3v22Handler implements TFID3Tags {
    private String title;
    private String artist;
    private String album;
    private String year;
    private String composer;
    private String genre;
    private String trackNumber;
    private String albumArtist;
    private String disc;
    private String uslt;
    private List<TFID3Comment> comments = new ArrayList<TFID3Comment>();

    public TFID3v22Handler(TFID3v2Frame frame)
            throws IOException, SAXException, TikaException {
        RawTagIterator tags = new RawV22TagIterator(frame);
        while (tags.hasNext()) {
            RawTag tag = tags.next();
            if (tag.name.equals("TT2")) {
                title = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("TP1")) {
                artist = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("TP2")) {
                albumArtist = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("TAL")) {
                album = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("TYE")) {
                year = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("TCM")) {
                composer = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("COM")) {
                comments.add( getComment(tag.data, 0, tag.data.length) ); 
            } else if (tag.name.equals("TRK")) {
                trackNumber = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("TPA")) {
                disc = getTagString(tag.data, 0, tag.data.length); 
            } else if (tag.name.equals("TCO")) {
                genre = extractGenre( getTagString(tag.data, 0, tag.data.length) );
            } else if (tag.name.equals("USLT")) {
            	uslt = getTagUSLTString(tag.data, 0, tag.data.length); 
            }
        }
    }
    
    private String getTagUSLTString(byte[] data, int offset, int length) {
        return TFID3v2Frame.getUSLTTagString(data, offset, length);
    }
    private String getTagString(byte[] data, int offset, int length) {
        return TFID3v2Frame.getTagString(data, offset, length);
    }
    private TFID3Comment getComment(byte[] data, int offset, int length) {
        return TFID3v2Frame.getComment(data, offset, length);
    }
    
    protected static String extractGenre(String rawGenre) {
       int open = rawGenre.indexOf("(");
       int close = rawGenre.indexOf(")");
       if (open == -1 && close == -1) {
          return rawGenre;
       } else if (open < close) {
           String genreStr = rawGenre.substring(0, open).trim();
           try {
               int genreID = Integer.parseInt(rawGenre.substring(open+1, close));
               return ID3Tags.GENRES[genreID];
           } catch(ArrayIndexOutOfBoundsException invalidNum) {
              return genreStr;
           } catch(NumberFormatException notANum) {
              return genreStr;
           }
       } else {
          return null;
       }
    }

    public boolean getTagsPresent() {
        return true;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }
    
    public String getComposer() {
        return composer;
    }

    public List<TFID3Comment> getComments() {
        return comments;
    }

    public String getGenre() {
        return genre;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public String getDisc() {
        return disc;
    }

    /**
     * ID3v22 doesn't have compilations,
     *  so returns null;
     */
    public String getCompilation() {
        return null;
    }
    
    public String getUslt() {
		return uslt;
	}

    private class RawV22TagIterator extends RawTagIterator {
        private RawV22TagIterator(TFID3v2Frame frame) {
            frame.super(3, 3, 1, 0);
        }
    }
}

