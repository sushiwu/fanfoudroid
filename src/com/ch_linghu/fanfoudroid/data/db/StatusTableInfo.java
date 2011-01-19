package com.ch_linghu.fanfoudroid.data.db;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.ch_linghu.fanfoudroid.data.Tweet;
import com.ch_linghu.fanfoudroid.helper.Utils;

/**
 * All information of status table
 *
 */
public final class StatusTableInfo {
    /**
     * Status Table 
     * 定长表格, 使各类型的数据均总保持在MAX_STATUS_NUM以下
     * 对于单类型的数据模拟列队形式, 遵循以先进先出的原则进行存储
     * 即在尾部插入新数据时, 若列队超过长度, 则自动删除头部旧数据以保持列队长度
     *
     */
    public static final class StatusTable implements BaseColumns {
        // Status Type
        public static final int TYPE_HOME = 1;
        public static final int TYPE_MENTION = 2;
        public static final int TYPE_USER = 3;
        
        public static final String TABLE_NAME = "status";
        public static final int MAX_STATUS_NUM = 20;
        
        public static final String FIELD_USER_ID = "uid";
        public static final String FIELD_USER_SCREEN_NAME = "screen_name";
        public static final String FIELD_PROFILE_IMAGE_URL = "profile_image_url";
        public static final String FIELD_CREATED_AT = "created_at";
        public static final String FIELD_TEXT = "text";
        public static final String FIELD_SOURCE = "source";
        public static final String FIELD_TRUNCATED = "truncated";
        public static final String FIELD_IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id";
        public static final String FIELD_IN_REPLY_TO_USER_ID = "in_reply_to_user_id";
        public static final String FIELD_IN_REPLY_TO_SCREEN_NAME = "in_reply_to_screen_name";
        public static final String FIELD_FAVORITED =  "favorited";
        public static final String FIELD_IS_UNREAD = "is_unread";
        public static final String FIELD_STATUS_TYPE = "status_type";
    //    private static final String FIELD_PHOTO_URL = "photo_url";
    //    private double latitude = -1;
    //    private double longitude = -1;
    //    private String thumbnail_pic;
    //    private String bmiddle_pic;
    //    private String original_pic;
        
        public static final String[] TABLE_STATUS_COLUMNS = new String[] {_ID, FIELD_USER_SCREEN_NAME,
            FIELD_TEXT, FIELD_PROFILE_IMAGE_URL, FIELD_IS_UNREAD, FIELD_CREATED_AT,
            FIELD_FAVORITED, FIELD_IN_REPLY_TO_STATUS_ID, FIELD_IN_REPLY_TO_USER_ID,
            FIELD_IN_REPLY_TO_SCREEN_NAME, FIELD_TRUNCATED,
            FIELD_SOURCE, FIELD_USER_ID, FIELD_STATUS_TYPE};
        
        public static final String STATUS_TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME + " (" 
            + _ID + " INTEGER not null,"
            + FIELD_STATUS_TYPE + " text not null, "
            + FIELD_USER_ID + " text not null, "
            + FIELD_USER_SCREEN_NAME + " text not null, "
            + FIELD_TEXT + " text not null, "
            + FIELD_PROFILE_IMAGE_URL + " text not null, "
            + FIELD_IS_UNREAD + " boolean not null, "
            + FIELD_CREATED_AT + " date not null, "
            + FIELD_FAVORITED + " boolean, "
            + FIELD_IN_REPLY_TO_STATUS_ID + " text, "
            + FIELD_IN_REPLY_TO_USER_ID + " text, "
            + FIELD_IN_REPLY_TO_SCREEN_NAME + " text, "
            + FIELD_SOURCE + " text not null, " 
            + FIELD_TRUNCATED + " boolean ,"
            + "PRIMARY KEY (" + _ID + ","+ FIELD_STATUS_TYPE  + "))";
        
        /**
         * 将游标解析为一条Tweet
         * 
         * @param cursor 返回前会关闭游标
         * @return
         */
        public static Tweet parseCursor(Cursor cursor) {
            Tweet tweet = new Tweet();
            tweet.id = cursor.getString(cursor.getColumnIndex(_ID));
            tweet.createdAt = Utils.parseDateTimeFromSqlite(cursor.getString(cursor.getColumnIndex(FIELD_CREATED_AT)));
            tweet.favorited = cursor.getString(cursor.getColumnIndex(FIELD_FAVORITED));
            tweet.screenName = cursor.getString(cursor.getColumnIndex(FIELD_USER_SCREEN_NAME));
            tweet.userId = cursor.getString(cursor.getColumnIndex(FIELD_USER_ID));
            tweet.text = cursor.getString(cursor.getColumnIndex(FIELD_TEXT));
            tweet.source = cursor.getString(cursor.getColumnIndex(FIELD_SOURCE));
            tweet.profileImageUrl = cursor.getString(cursor.getColumnIndex(FIELD_PROFILE_IMAGE_URL));
            tweet.inReplyToScreenName = cursor.getString(cursor.getColumnIndex(FIELD_IN_REPLY_TO_SCREEN_NAME));
            tweet.inReplyToStatusId = cursor.getString(cursor.getColumnIndex(FIELD_IN_REPLY_TO_STATUS_ID));
            tweet.inReplyToUserId = cursor.getString(cursor.getColumnIndex(FIELD_IN_REPLY_TO_USER_ID));
            tweet.truncated = cursor.getString(cursor.getColumnIndex(FIELD_TRUNCATED));
            tweet.setStatusType(cursor.getInt(cursor.getColumnIndex(FIELD_STATUS_TYPE)) );
            
            cursor.close(); 
            return tweet;
        }
    }
}