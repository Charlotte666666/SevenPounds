package me.lancer.sevenpounds.mvp.music;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class MusicModel {

    IMusicPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String reviewerUrl = "https://music.douban.com/review/pop/?start=";
    String topMusicUrl = "https://music.douban.com/top250?start=";
    String searchMusicUrl = "https://music.douban.com/subject_search?search_text=";

    public MusicModel(IMusicPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadReviewer(int pager) {//评论
        String content = contentGetterSetter.getContentFromHtml("Music.loadReviewer", reviewerUrl+pager);
        List<MusicBean> list;
        if (!content.contains("获取失败!")) {
            list = getReviewerFromContent(content);
            presenter.loadReviewerSuccess(list);
        } else {
            presenter.loadReviewerFailure(content);
            Log.e("loadReviewer", content);
        }
    }

    public void loadTopMusic(int pager) {//音乐top250
        String content = contentGetterSetter.getContentFromHtml("Music.loadTopMusic", topMusicUrl+pager);
        List<MusicBean> list;
        if (!content.contains("获取失败!")) {
            list = getTopMusicFromContent(content);
            presenter.loadTopMusicSuccess(list);
        } else {
            presenter.loadTopMusicFailure(content);
            Log.e("loadTopMusic", content);
        }
    }

    public void loadTopMusic(String query) {//电影top250
        String content = contentGetterSetter.getContentFromHtml("Music.loadTopMusic", searchMusicUrl + query);
        List<MusicBean> list;
        if (!content.contains("获取失败!")) {
            list = getQueryMusicFromContent(content);
            presenter.loadTopMusicSuccess(list);
        } else {
            presenter.loadTopMusicFailure(content);
            Log.e("loadTopMovie", content);
        }
    }

    public void loadReviewerDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml("Music.loadReviewerDetail", url);
        MusicBean bean;
        if (!content.contains("获取失败!")) {
            bean = getReviewerDetailFromContent(content);
            presenter.loadReviewerDetailSuccess(bean);
        } else {
            presenter.loadReviewerDetailFailure(content);
            Log.e("loadReviewerDetail", content);
        }
    }

    public void loadTopDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml("Music.loadTopDetail", url);
        MusicBean bean;
        if (!content.contains("获取失败!")) {
            bean = getTopDetailFromContent(content);
            presenter.loadTopDetailSuccess(bean);
        } else {
            presenter.loadTopDetailFailure(content);
            Log.e("loadTopDetail", content);
        }
    }

    public List<MusicBean> getReviewerFromContent(String content) {
        List<MusicBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("main review-item");
        for (int i = 0; i < elements.size(); i++) {
            MusicBean mbItem = new MusicBean();
            mbItem.setSubTitle(elements.get(i).getElementsByTag("img").attr("alt"));
            mbItem.setMainTitle(elements.get(i).getElementsByClass("title-link").text());
            mbItem.setAuthor(elements.get(i).getElementsByClass("author").text());
            mbItem.setStar(elements.get(i).getElementsByClass("main-title-hide").text());
            mbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            mbItem.setMainLink(elements.get(i).getElementsByTag("a").attr("href"));
            mbItem.setSubLink(elements.get(i).getElementsByTag("a").get(1).attr("href"));
            list.add(mbItem);
        }
        return list;
    }

    public List<MusicBean> getTopMusicFromContent(String content) {
        List<MusicBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("item");
        for (int i = 0; i < elements.size(); i++) {
            MusicBean mbItem = new MusicBean();
            mbItem.setMainTitle(elements.get(i).getElementsByClass("nbg").attr("title"));
            mbItem.setStar(elements.get(i).getElementsByClass("rating_nums").text());
            mbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            mbItem.setMainLink(elements.get(i).getElementsByTag("a").attr("href"));
            list.add(mbItem);
        }
        return list;
    }

    public List<MusicBean> getQueryMusicFromContent(String content) {
        List<MusicBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("item");
        for (int i = 0; i < elements.size(); i++) {
            MusicBean mbItem = new MusicBean();
            mbItem.setMainTitle(elements.get(i).getElementsByClass("nbg").attr("title"));
            mbItem.setStar(elements.get(i).getElementsByClass("rating_nums").text());
            mbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            mbItem.setMainLink(elements.get(i).getElementsByTag("a").attr("href"));
            list.add(mbItem);
        }
        return list;
    }

    public MusicBean getReviewerDetailFromContent(String content) {
        MusicBean bean = new MusicBean();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        bean.setSubTitle(element.getElementsByClass("info-list").get(0).html());
        bean.setContent("— 乐评 —<br>"+element.getElementsByClass("review-content clearfix").get(0).html());
        return bean;
    }

    public MusicBean getTopDetailFromContent(String content) {
        MusicBean bean = new MusicBean();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        bean.setSubTitle(element.getElementById("info").html());
        if (element.getElementsByClass("all hidden").size()>0) {
            bean.setContent("— 简介 —<br>" + element.getElementsByClass("all hidden").get(0).html());
        }else{
            bean.setContent("— 简介 —<br>" + element.getElementById("link-report").html());
        }
        return bean;
    }
}
