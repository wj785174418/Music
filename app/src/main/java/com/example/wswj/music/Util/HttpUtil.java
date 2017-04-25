package com.example.wswj.music.Util;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.example.wswj.music.Model.RollPicture;
import com.example.wswj.music.Model.SongMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/23.
 */

public class HttpUtil {

    //更新数据
    public static final int UPDATE = 100;
    //更新RollBanner
    public static final int UPDATE_ROLLBANNER = 101;
    //新插入数据
    public static final int INSERT= 102;

    //没有更多加载
    public static final int LOAD_OVER = 200;
    /**
     * 百度音乐接口
     * */


    //百度音乐热点图
    private static final String HOT_PIC = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.plaza.getFocusPic&format=json";

    //百度音乐推荐歌单
    private static final String RECOMMEND = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.getHotGeDanAndOfficial&num=%s&format=json";

    //百度音乐热门歌单
    private static final String HOT = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedan&page_no=1&page_size=%s&format=json";


    //client
    private static OkHttpClient client = new OkHttpClient();


    /**
     * @param url Get请求的地址
     * @param callback 回调接口
     */
    public static void sendGETRequest(String url, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", getUserAgent())
                .build();
//        LogUtil.d("test", request.toString());
        client.newCall(request).enqueue(callback);
    }


    /**
     * 首页轮播图
     */
    public static void hotPicture(@NonNull final List<RollPicture> rollPicList, final Handler handler) {

        HttpUtil.sendGETRequest(HOT_PIC, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resultStr = response.body().string();
                    JSONObject result = new JSONObject(resultStr);
                    if (result.getInt("error_code") == 22000) {
                        JSONArray pics = result.getJSONArray("pic");
                        for (int i = 0; i < pics.length(); i++) {
                            JSONObject pic = pics.getJSONObject(i);
                            if (pic.getInt("type") == 2) {
                                String albumID = pic.getString("code");
                                String picUrl = pic.getString("randpic");
                                RollPicture rollPic = new RollPicture(albumID, picUrl);
                                rollPicList.add(rollPic);
                            }
                        }
                    }
                    Message msg = new Message();
                    msg.what = UPDATE_ROLLBANNER;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 请求推荐歌单
     * @param songMenuList 歌单列表
     * @param num          请求歌单数量
     * @param handler      handler
     * @param requestType  请求类型
     */
    public static void recommendSongMenu(final List<SongMenu> songMenuList, final int num, final Handler handler, final int requestType) {
        String url = String.format(RECOMMEND, num);
        HttpUtil.sendGETRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resultStr = response.body().string();
                    JSONObject result = new JSONObject(resultStr);
                    if (result.getInt("error_code") == 22000) {
                        JSONArray list = result.getJSONObject("content").getJSONArray("list");

                        int length = 0;
                        if (requestType == UPDATE) {
                            length = 0;
                            songMenuList.clear();
                        } else if (requestType == INSERT) {
                            length = songMenuList.size();
                        }

                        for (int i = length; i < list.length(); i++) {
                            JSONObject aSongMenu = list.getJSONObject(i);
                            String songMenuID = aSongMenu.getString("listid");
                            String picUrl = aSongMenu.getString("pic");
                            String title = aSongMenu.getString("title");
                            String listenNum = aSongMenu.getString("listenum");
                            SongMenu songMenu = new SongMenu
                                    (songMenuID, title, null, picUrl, listenNum);
                            songMenuList.add(songMenu);
                        }
                        Message msg = new Message();
                        if (num > list.length()) {
                            msg.what = LOAD_OVER;
                        } else {
                            msg.what = requestType;
                        }
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 热门歌单
     * @param songMenuList 歌单List
     * @param num          请求数量
     * @param handler      handler
     * @param requestType  请求类型
     */
    public static void hotSongMenu(final List<SongMenu> songMenuList, final int num, final Handler handler, final int requestType) {
        String url = String.format(HOT, num);
        HttpUtil.sendGETRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resultStr = response.body().string();
                    JSONObject result = new JSONObject(resultStr);
                    if (result.getInt("error_code") == 22000) {
                        JSONArray list = result.getJSONArray("content");

                        int length = 0;
                        if (requestType == UPDATE) {
                            length = 0;
                            songMenuList.clear();
                        } else if (requestType == INSERT) {
                            length = songMenuList.size();
                        }

                        for (int i = length; i < list.length(); i++) {
                            JSONObject aSongMenu = list.getJSONObject(i);
                            String songMenuID = aSongMenu.getString("listid");
                            String picUrl = aSongMenu.getString("pic_300");
                            String title = aSongMenu.getString("title");
                            String desc = aSongMenu.getString("desc");
                            String listenNum = aSongMenu.getString("listenum");
                            SongMenu songMenu = new SongMenu
                                    (songMenuID, title, desc, picUrl, listenNum);
                            songMenuList.add(songMenu);
                        }
                        Message msg = new Message();
                        if (num > list.length()) {
                            msg.what = LOAD_OVER;
                        } else {
                            msg.what = requestType;
                        }
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @return 系统UserAgent
     */
    private static String getUserAgent() {
        return System.getProperty("http.agent");
    }

}
