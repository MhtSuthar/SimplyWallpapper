package com.wall.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "totalHits",
        "hits",
        "total"
})
public class Wallpaper {

    public Wallpaper(){

    }

    @JsonProperty("totalHits")
    private Integer totalHits;
    @JsonProperty("hits")
    private List<Hit> hits = null;
    @JsonProperty("total")
    private Integer total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("totalHits")
    public Integer getTotalHits() {
        return totalHits;
    }

    @JsonProperty("totalHits")
    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    @JsonProperty("hits")
    public List<Hit> getHits() {
        return hits;
    }

    @JsonProperty("hits")
    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "previewHeight",
            "likes",
            "favorites",
            "tags",
            "webformatHeight",
            "views",
            "webformatWidth",
            "previewWidth",
            "comments",
            "downloads",
            "pageURL",
            "previewURL",
            "webformatURL",
            "imageWidth",
            "user_id",
            "user",
            "type",
            "id",
            "userImageURL",
            "imageHeight"
    })

    public static class Hit implements Serializable {

        public Hit(){

        }

        @JsonProperty("previewHeight")
        private Integer previewHeight;
        @JsonProperty("likes")
        private Integer likes;
        @JsonProperty("favorites")
        private Integer favorites;
        @JsonProperty("tags")
        private String tags;
        @JsonProperty("webformatHeight")
        private Integer webformatHeight;
        @JsonProperty("views")
        private Integer views;
        @JsonProperty("webformatWidth")
        private Integer webformatWidth;
        @JsonProperty("previewWidth")
        private Integer previewWidth;
        @JsonProperty("comments")
        private Integer comments;
        @JsonProperty("downloads")
        private Integer downloads;
        @JsonProperty("pageURL")
        private String pageURL;
        @JsonProperty("previewURL")
        private String previewURL;
        @JsonProperty("webformatURL")
        private String webformatURL;
        @JsonProperty("imageWidth")
        private Integer imageWidth;
        @JsonProperty("user_id")
        private Integer userId;
        @JsonProperty("user")
        private String user;
        @JsonProperty("type")
        private String type;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("userImageURL")
        private String userImageURL;
        @JsonProperty("imageHeight")
        private Integer imageHeight;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        public int typeLoadMore = 0;

        @JsonProperty("previewHeight")
        public Integer getPreviewHeight() {
            return previewHeight;
        }

        @JsonProperty("previewHeight")
        public void setPreviewHeight(Integer previewHeight) {
            this.previewHeight = previewHeight;
        }

        @JsonProperty("likes")
        public Integer getLikes() {
            return likes;
        }

        @JsonProperty("likes")
        public void setLikes(Integer likes) {
            this.likes = likes;
        }

        @JsonProperty("favorites")
        public Integer getFavorites() {
            return favorites;
        }

        @JsonProperty("favorites")
        public void setFavorites(Integer favorites) {
            this.favorites = favorites;
        }

        @JsonProperty("tags")
        public String getTags() {
            return tags;
        }

        @JsonProperty("tags")
        public void setTags(String tags) {
            this.tags = tags;
        }

        @JsonProperty("webformatHeight")
        public Integer getWebformatHeight() {
            return webformatHeight;
        }

        @JsonProperty("webformatHeight")
        public void setWebformatHeight(Integer webformatHeight) {
            this.webformatHeight = webformatHeight;
        }

        @JsonProperty("views")
        public Integer getViews() {
            return views;
        }

        @JsonProperty("views")
        public void setViews(Integer views) {
            this.views = views;
        }

        @JsonProperty("webformatWidth")
        public Integer getWebformatWidth() {
            return webformatWidth;
        }

        @JsonProperty("webformatWidth")
        public void setWebformatWidth(Integer webformatWidth) {
            this.webformatWidth = webformatWidth;
        }

        @JsonProperty("previewWidth")
        public Integer getPreviewWidth() {
            return previewWidth;
        }

        @JsonProperty("previewWidth")
        public void setPreviewWidth(Integer previewWidth) {
            this.previewWidth = previewWidth;
        }

        @JsonProperty("comments")
        public Integer getComments() {
            return comments;
        }

        @JsonProperty("comments")
        public void setComments(Integer comments) {
            this.comments = comments;
        }

        @JsonProperty("downloads")
        public Integer getDownloads() {
            return downloads;
        }

        @JsonProperty("downloads")
        public void setDownloads(Integer downloads) {
            this.downloads = downloads;
        }

        @JsonProperty("pageURL")
        public String getPageURL() {
            return pageURL;
        }

        @JsonProperty("pageURL")
        public void setPageURL(String pageURL) {
            this.pageURL = pageURL;
        }

        @JsonProperty("previewURL")
        public String getPreviewURL() {
            return previewURL;
        }

        @JsonProperty("previewURL")
        public void setPreviewURL(String previewURL) {
            this.previewURL = previewURL;
        }

        @JsonProperty("webformatURL")
        public String getWebformatURL() {
            return webformatURL;
        }

        @JsonProperty("webformatURL")
        public void setWebformatURL(String webformatURL) {
            this.webformatURL = webformatURL;
        }

        @JsonProperty("imageWidth")
        public Integer getImageWidth() {
            return imageWidth;
        }

        @JsonProperty("imageWidth")
        public void setImageWidth(Integer imageWidth) {
            this.imageWidth = imageWidth;
        }

        @JsonProperty("user_id")
        public Integer getUserId() {
            return userId;
        }

        @JsonProperty("user_id")
        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        @JsonProperty("user")
        public String getUser() {
            return user;
        }

        @JsonProperty("user")
        public void setUser(String user) {
            this.user = user;
        }

        @JsonProperty("type")
        public String getType() {
            return type;
        }

        @JsonProperty("type")
        public void setType(String type) {
            this.type = type;
        }

        @JsonProperty("id")
        public Integer getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(Integer id) {
            this.id = id;
        }

        @JsonProperty("userImageURL")
        public String getUserImageURL() {
            return userImageURL;
        }

        @JsonProperty("userImageURL")
        public void setUserImageURL(String userImageURL) {
            this.userImageURL = userImageURL;
        }

        @JsonProperty("imageHeight")
        public Integer getImageHeight() {
            return imageHeight;
        }

        @JsonProperty("imageHeight")
        public void setImageHeight(Integer imageHeight) {
            this.imageHeight = imageHeight;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}



