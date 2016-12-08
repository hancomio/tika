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
package org.apache.tika.parser.mp3;

import java.util.Collections;
import java.util.List;

/**
 * Takes an array of {@link ID3Tags} in preference order, and when asked for
 * a given tag, will return it from the first {@link ID3Tags} that has it.
 */
public class TFCompositeTagHandler implements TFID3Tags {

    private TFID3Tags[] tags;

    public TFCompositeTagHandler(TFID3Tags[] tags) {
        this.tags = tags;
    }

    public boolean getTagsPresent() {
        for (TFID3Tags tag : tags) {
            if (tag.getTagsPresent()) {
                return true;
            }
        }
        return false;
    }

    public String getTitle() {
        for (TFID3Tags tag : tags) {
            if (tag.getTitle() != null) {
                return tag.getTitle();
            }
        }
        return null;
    }

    public String getArtist() {
        for (TFID3Tags tag : tags) {
            if (tag.getArtist() != null) {
                return tag.getArtist();
            }
        }
        return null;
    }

    public String getAlbum() {
        for (TFID3Tags tag : tags) {
            if (tag.getAlbum() != null) {
                return tag.getAlbum();
            }
        }
        return null;
    }

    public String getComposer() {
        for (TFID3Tags tag : tags) {
            if (tag.getComposer() != null) {
                return tag.getComposer();
            }
        }
        return null;
    }

    public String getYear() {
        for (TFID3Tags tag : tags) {
            if (tag.getYear() != null) {
                return tag.getYear();
            }
        }
        return null;
    }

    public List<TFID3Comment> getComments() {
        for (TFID3Tags tag : tags) {
            List<TFID3Comment> comments = tag.getComments();
            if (comments != null && comments.size() > 0) {
                return comments;
            }
        }
        return Collections.emptyList();
    }

    public String getGenre() {
        for (TFID3Tags tag : tags) {
            if (tag.getGenre() != null) {
                return tag.getGenre();
            }
        }
        return null;
    }
    
    public String getUslt() {
        for (TFID3Tags tag : tags) {
            if (tag.getUslt() != null) {
                return tag.getUslt();
            }
        }
        return null;
    }

    public String getTrackNumber() {
        for (TFID3Tags tag : tags) {
            if (tag.getTrackNumber() != null) {
                return tag.getTrackNumber();
            }
        }
        return null;
    }

    public String getAlbumArtist() {
        for (TFID3Tags tag : tags) {
            if (tag.getAlbumArtist() != null) {
                return tag.getAlbumArtist();
            }
        }
        return null;
    }

    public String getDisc() {
        for (TFID3Tags tag : tags) {
            if (tag.getDisc() != null) {
                return tag.getDisc();
            }
        }
        return null;
    }

    public String getCompilation() {
        for (TFID3Tags tag : tags) {
            if (tag.getCompilation() != null) {
                return tag.getCompilation();
            }
        }
        return null;
    }
}
