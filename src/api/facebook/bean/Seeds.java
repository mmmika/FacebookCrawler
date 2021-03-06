package api.facebook.bean;

import java.sql.Timestamp;

/**
 * Seeds entity. @author MyEclipse Persistence Tools
 */

public class Seeds
{

	// Fields

	private Integer seedsId;
	private String name;
	private String userName;
	private String facebookId;
	private Timestamp lastUpdate;
	private String about;
	private String bio;
	private Timestamp birthday;
	private String category;
	private String hometown;
	private Integer likes;
	private String link;
	private String pageName;
	private String website;
	private String postsPreviousPage;
	private String postsNextPage;
	private String feedsPreviousPage;
	private String feedsNextPage;

	// Constructors

	/** default constructor */
	public Seeds() {
	}

	/** minimal constructor */
	public Seeds(String name, String userName) {
		this.name = name;
		this.userName = userName;
	}

	/** full constructor */
	public Seeds(String name, String userName, String facebookId,
			Timestamp lastUpdate, String nextPage, String about,
			Timestamp birthday, String category, String hometown,
			Integer likes, String link, String pageName) {
		this.name = name;
		this.userName = userName;
		this.facebookId = facebookId;
		this.lastUpdate = lastUpdate;
		this.about = about;
		this.birthday = birthday;
		this.category = category;
		this.hometown = hometown;
		this.likes = likes;
		this.link = link;
		this.pageName = pageName;

	}

	// Property accessors

	public Integer getSeedsId() {
		return this.seedsId;
	}

	public void setSeedsId(Integer seedsId) {
		this.seedsId = seedsId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFacebookId() {
		return this.facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPostsNextPage() {
		return postsNextPage;
	}

	public void setPostsNextPage(String postsNextPage) {
		this.postsNextPage = postsNextPage;
	}

	public String getFeedsNextPage() {
		return feedsNextPage;
	}

	public void setFeedsNextPage(String feedsNextPage) {
		this.feedsNextPage = feedsNextPage;
	}

	public String getPostsPreviousPage() {
		return postsPreviousPage;
	}

	public void setPostsPreviousPage(String postsPreviousPage) {
		this.postsPreviousPage = postsPreviousPage;
	}

	public String getFeedsPreviousPage() {
		return feedsPreviousPage;
	}

	public void setFeedsPreviousPage(String feedsPreviousPage) {
		this.feedsPreviousPage = feedsPreviousPage;
	}
	
	
}