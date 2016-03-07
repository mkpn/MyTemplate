package com.template.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by makoto on 2016/03/08.
 */
public class YoutubeSearchResponse {
    @Expose
    private String kind;

    @Expose
    private String etag;

    @Expose
    private String nextPageToken;

    @Expose
    private String regionCode;

    @Expose
    private PageInfo pageInfo;

    public class PageInfo {
        @Expose
        private int totalResults;
        @Expose
        private int resultsPerPage;

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public int getResultsPerPage() {
            return resultsPerPage;
        }

        public void setResultsPerPage(int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
        }
    }

    @Expose
    private List<SearchResult> items;

    public class SearchResult {
        @Expose
        private String kind;
        @Expose
        private String etag;
        @Expose
        private Id id;

        public class Id {
            @Expose
            private String kind;
            @Expose
            private String videoId;

            public String getKind() {
                return kind;
            }

            public void setKind(String kind) {
                this.kind = kind;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }
        }

        @Expose
        private Snippet snippet;

        public class Snippet {
            @Expose
            private String publishedAt;

            @Expose
            private String channelId;

            @Expose
            private String title;

            @Expose
            private String description;

            @Expose
            private Thumbnails thumbnails;

            public class Thumbnails {
                @Expose
                private Default aDefault;

                public class Default {
                    @Expose
                    private String url;

                    @Expose
                    private int width;

                    @Expose
                    private int height;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }
                }

                @Expose
                private Medium medium;

                public class Medium {
                    @Expose
                    private String url;
                    @Expose
                    private int width;
                    @Expose
                    private int height;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }
                }

                @Expose
                private High high;

                public class High {
                    @Expose
                    private String url;
                    @Expose
                    private int width;
                    @Expose
                    private int height;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }
                }

                public Default getaDefault() {
                    return aDefault;
                }

                public void setaDefault(Default aDefault) {
                    this.aDefault = aDefault;
                }

                public Medium getMedium() {
                    return medium;
                }

                public void setMedium(Medium medium) {
                    this.medium = medium;
                }

                public High getHigh() {
                    return high;
                }

                public void setHigh(High high) {
                    this.high = high;
                }
            }

            @Expose
            private String channelTitle;
            @Expose
            private String liveBroadcastContent;

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Thumbnails getThumbnails() {
                return thumbnails;
            }

            public void setThumbnails(Thumbnails thumbnails) {
                this.thumbnails = thumbnails;
            }

            public String getChannelTitle() {
                return channelTitle;
            }

            public void setChannelTitle(String channelTitle) {
                this.channelTitle = channelTitle;
            }

            public String getLiveBroadcastContent() {
                return liveBroadcastContent;
            }

            public void setLiveBroadcastContent(String liveBroadcastContent) {
                this.liveBroadcastContent = liveBroadcastContent;
            }
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getEtag() {
            return etag;
        }

        public void setEtag(String etag) {
            this.etag = etag;
        }

        public Id getId() {
            return id;
        }

        public void setId(Id id) {
            this.id = id;
        }

        public Snippet getSnippet() {
            return snippet;
        }

        public void setSnippet(Snippet snippet) {
            this.snippet = snippet;
        }
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<SearchResult> getItems() {
        return items;
    }

    public void setItems(List<SearchResult> items) {
        this.items = items;
    }
}
