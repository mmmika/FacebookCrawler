package api.facebook.bean;

import java.sql.Timestamp;

/**
 * Feeds entity. @author MyEclipse Persistence Tools
 */

public class Feeds
{

	// Fields
	//这个属性用于标示返回的posts 的json数据的状态，error表示出现错误；empty表示已经爬取到当前的尽头（无论是历史数据还是未来数据）
	private String status="";
	private String codeMessage="";
	
	
	private Integer feedId;
	private String messageId;
	private String message;
	private String fromUserName;
	private String fromUserId;
	private Integer seedsId;
	private String description;
	private String name;
	private String link;
	private String picture;
	private String type;
	private Timestamp createdTime;
	private Timestamp insertTime;
	private String feedsPreviousPage;
	private String feedsNextPage; 

	// Constructors

	/** default constructor */
	public Feeds() {
	}

	/** minimal constructor */
	public Feeds(String messageId, String fromUserName,
			String fromUserId, Timestamp createdTime, Timestamp insertTime) {
		this.messageId = messageId;
		this.fromUserName = fromUserName;
		this.fromUserId = fromUserId;
		this.createdTime = createdTime;
		this.insertTime = insertTime;
	}

	/** full constructor */
	public Feeds(String messageId, String message,
			String fromUserName, String fromUserId, String description,
			String name, String link, String picture, String type,
			Timestamp createdTime, Timestamp insertTime) {
		this.messageId = messageId;
		this.message = message;
		this.fromUserName = fromUserName;
		this.fromUserId = fromUserId;
		this.description = description;
		this.name = name;
		this.link = link;
		this.picture = picture;
		this.type = type;
		this.createdTime = createdTime;
		this.insertTime = insertTime;
	}

	// Property accessors

	public Integer getFeedId() {
		return this.feedId;
	}

	public void setFeedId(Integer feedId) {
		this.feedId = feedId;
	}


	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromUserName() {
		return this.fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromUserId() {
		return this.fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	

	public Integer getSeedsId() {
		return seedsId;
	}

	public void setSeedsId(Integer seedsId) {
		this.seedsId = seedsId;
	}

	public String getFeedsPreviousPage() {
		return feedsPreviousPage;
	}

	public void setFeedsPreviousPage(String feedsPreviousPage) {
		this.feedsPreviousPage = feedsPreviousPage;
	}

	public String getFeedsNextPage() {
		return feedsNextPage;
	}

	public void setFeedsNextPage(String feedsNextPage) {
		this.feedsNextPage = feedsNextPage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCodeMessage() {
		return codeMessage;
	}

	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}
	
}