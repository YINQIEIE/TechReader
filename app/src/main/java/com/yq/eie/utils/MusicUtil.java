package com.yq.eie.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;
import com.yq.eie.entity.Album;
import com.yq.eie.entity.Artist;
import com.yq.eie.entity.Folder;
import com.yq.eie.entity.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqi on 2017/9/11.
 */

public class MusicUtil {

    private static final int MIN_DURATION = 60 * 1000;//最短时长一分钟
    private static final int MIN_SIZE = 1024 * 1024;//最小文件 1M

    public static ContentResolver getMusicResolver(Context context) {
        return context.getApplicationContext().getContentResolver();
    }

    /**
     * 歌曲查询列名称
     */
    public static final String[] PRO_SONG = new String[]{Media._ID, Media.TITLE, Media.ARTIST_ID, Media.ARTIST, Media.DURATION, Media.ALBUM_ID, Media.ALBUM, Media.SIZE, Media.DATA};
    /**
     * 歌曲查询条件
     */
    public static final String SEL_SONG = Media.SIZE + " > ? and " + Media.DURATION + " > ?";

    public static final String[] SEL_ARGS = new String[]{MIN_SIZE + "", MIN_DURATION + ""};

    public static List<Song> getSongList(Context context) {
        Cursor cursor = getMusicResolver(context).query(Media.EXTERNAL_CONTENT_URI, PRO_SONG, SEL_SONG, SEL_ARGS, Media.DEFAULT_SORT_ORDER);
        return getSongListByCursor(cursor);
    }

    /**
     * 根据歌手 id 获取歌曲
     *
     * @param context
     * @param id      歌手 ID
     * @return
     */
    public static List<Song> getSongsByArtistId(Context context, int id) {
        String sel = SEL_SONG + " and " + Media.ARTIST_ID + " = " + id;
        Cursor cursor = getMusicResolver(context).query(Media.EXTERNAL_CONTENT_URI, PRO_SONG, sel, SEL_ARGS, Media.DEFAULT_SORT_ORDER);
        return getSongListByCursor(cursor);
    }

    /**
     * 根据专辑 id 获取歌曲
     *
     * @param context
     * @param id      歌手 ID
     * @return
     */
    public static List<Song> getSongsByAlbumId(Context context, int id) {
        String sel = SEL_SONG + " and " + Media.ALBUM_ID + " = " + id;
        Cursor cursor = getMusicResolver(context).query(Media.EXTERNAL_CONTENT_URI, PRO_SONG, sel, SEL_ARGS, Media.DEFAULT_SORT_ORDER);
        return getSongListByCursor(cursor);
    }

    /**
     * 根据专辑文件夹路径获取歌曲
     *
     * @param context
     * @param path    文件夹路径
     * @return
     */
    public static List<Song> getSongsByFilePath(Context context, String path) {
        List<Song> songList = getSongList(context);
        List<Song> reslutList = new ArrayList<>();
        for (Song song : songList) {
            String itemPath = song.getPath();
            if (itemPath.substring(0, itemPath.lastIndexOf(File.separator)).equals(path)) {
                reslutList.add(song);
            }
        }
        return reslutList;
    }


    private static final String exp = "[^(a-zA-Z\\u4e00-\\u9fa5)]";

    public static List<Song> getSongListByCursor(Cursor cursor) {
        try {
            List<Song> songs = new ArrayList<>();
            if (null == cursor) return songs;
            while (cursor.moveToNext()) {
                Song song = new Song();
                song.setSongId(cursor.getInt(cursor.getColumnIndex(Media._ID)));
                song.setSongName(cursor.getString(cursor.getColumnIndex(Media.TITLE)));
                Log.i("name", cursor.getString(cursor.getColumnIndex(Media.TITLE)).replaceAll(exp, ""));
                song.setArtistId(cursor.getInt(cursor.getColumnIndex(Media.ARTIST_ID)));
                song.setArtist(cursor.getString(cursor.getColumnIndex(Media.ARTIST)));
                song.setDuration(cursor.getInt(cursor.getColumnIndex(Media.DURATION)));
                song.setAlbumId(cursor.getInt(cursor.getColumnIndex(Media.ALBUM_ID)));
                song.setAlbum(cursor.getString(cursor.getColumnIndex(Media.ALBUM)));
                song.setSize(cursor.getInt(cursor.getColumnIndex(Media.SIZE)));
                song.setPath(cursor.getString(cursor.getColumnIndex(Media.DATA)));
                song.setFirstChar(Pinyin.toPinyin(song.getSongName().charAt(0)).substring(0, 1).toUpperCase());
                songs.add(song);
            }
            return songs;
        } finally {
            if (null != cursor && !cursor.isClosed())
                cursor.close();
        }
    }

    /**
     * 歌手查询列名称
     */
    public static final String[] PRO_ATRIST = new String[]{MediaStore.Audio.Artists._ID, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Artists.NUMBER_OF_TRACKS};

    /**
     * 歌手查询条件
     */
    public static final String SEL_ARTIST = MediaStore.Audio.Artists._ID
            + " in (" +
            "select distinct " + Media.ARTIST_ID + " FROM audio_meta WHERE " + Media.SIZE + " > " + MIN_SIZE + " and " + Media.DURATION + " > " + MIN_DURATION
            + ")";

    /**
     * 获取歌手信息
     *
     * @param context
     * @return
     */
    public static List<Artist> getArtists(Context context) {
        Cursor cursor = getMusicResolver(context).query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, PRO_ATRIST, SEL_ARTIST, null, MediaStore.Audio.Artists.DEFAULT_SORT_ORDER/*A - Z*/);
        return getArtistListByCursor(cursor);
    }

    public static List<Artist> getArtistListByCursor(Cursor cursor) {
        try {
            List<Artist> artists = new ArrayList<>();
            if (null == cursor) return artists;
            while (cursor.moveToNext()) {
                Artist artist = new Artist();
                artist.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)));
                artist.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
                artist.setCount(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)));
                artist.setFirstChar(Pinyin.toPinyin(artist.getName().charAt(0)).substring(0, 1).toUpperCase());
                artists.add(artist);
            }
            return artists;
        } finally {
            if (null != cursor && !cursor.isClosed())
                cursor.close();
        }
    }

   /* */
    /**
     * 查询歌手图片
     *//*
    private static String SEL_QUERY_ARTIST = MediaStore.Audio.Artists._ID + " = ?";

    public void getArtistById(Context context, int id) {
        Cursor cursor = getMusicResolver(context).query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, PRO_ATRIST, )
    }*/
    private static final String[] PRO_ALBUM = new String[]{
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS
    };
    private static String SEL_ALBUM = MediaStore.Audio.Albums._ID + " in (" +
            "select distinct " + MediaStore.Audio.Albums.ALBUM_ID + " FROM audio_meta WHERE " + Media.DURATION + " > " + MIN_DURATION + " and " + Media.SIZE + " > " + MIN_SIZE + ")";

    public static List<Album> getAlbums(Context context) {
        Cursor cursor = getMusicResolver(context).query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, PRO_ALBUM,
                SEL_ALBUM, null, MediaStore.Audio.Albums.DEFAULT_SORT_ORDER
        );
        return getAlbumsByCursor(cursor);

    }

    private static List<Album> getAlbumsByCursor(Cursor cursor) {
        List<Album> albums = new ArrayList<>();
        if (cursor == null) return albums;
        while (cursor.moveToNext()) {
            Album album = new Album();
            album.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
            album.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
            album.setAlbum_art(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
            album.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
            album.setCount(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));
            album.setFirstChar(Pinyin.toPinyin(album.getName().charAt(0)).substring(0, 1).toUpperCase());
            albums.add(album);
        }
        cursor.close();
        return albums;
    }

    private static final String[] PRO_FOLDER = new String[]{
            MediaStore.Files.FileColumns.DATA
    };
    private static final String SEL_FOLDER = MediaStore.Files.FileColumns.MEDIA_TYPE + " = " + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO
            + " and (" + MediaStore.Files.FileColumns.DATA + " like '%.mp3' or '%.wma' or '%.aac')"
            + " and " + Media.DURATION + " > " + MIN_DURATION
            + " and " + Media.SIZE + " > " + MIN_SIZE + ")"
            + " group by (" + MediaStore.Files.FileColumns.PARENT;

    public static List<Folder> getFolders(Context context) {
        Cursor cursor = getMusicResolver(context).query(MediaStore.Files.getContentUri("external"), PRO_FOLDER, SEL_FOLDER, null, null);
        List<Folder> folders = getFoldersByCursor(cursor);
        List<Song> songs = getSongList(context);
        for (Folder f : folders) {
            for (Song song : songs) {
                if (song.getPath().contains(f.getPath())) f.setCount(f.getCount() + 1);//歌曲数加 1
            }
        }
        return folders;
    }

    public static List<Folder> getFoldersByCursor(Cursor cursor) {
        List<Folder> folders = new ArrayList<>();
        while (cursor.moveToNext()) {
            Folder folder = new Folder();
            String path = cursor.getString(0);
            Log.i("path", path);
            path = path.substring(0, path.lastIndexOf(File.separator));
            folder.setPath(path);// storage/emulate/music
            folder.setName(path.substring(path.lastIndexOf(File.separator) + 1));//music
            Log.i("path", folder.toString());
            folders.add(folder);
        }
        return folders;
    }


}
