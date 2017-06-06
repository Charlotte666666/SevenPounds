package me.lancer.sevenpounds.mvp.game;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.mvp.news.NewsBean;
import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class GameModel {

    IGamePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String featuredUrl = "http://store.steampowered.com/api/featured?l=cn";
    String categoriesUrl = "http://store.steampowered.com/api/featuredcategories?l=cn";
    String appdetailsUrl = "http://store.steampowered.com/api/appdetails?l=cn&appids=";

    public GameModel(IGamePresenter presenter) {
        this.presenter = presenter;
    }

    public void loadFeatured() {
        String content = contentGetterSetter.getContentFromHtml("Game.loadFeatured", featuredUrl);
        List<GameBean> list;
        if (!content.contains("失败")) {
            list = getFeaturedFromContent(content);
            presenter.loadFeaturedSuccess(list);
        } else {
            presenter.loadFeaturedFailure(content);
            Log.e("loadFeatured", content);
        }
    }

    public void loadCategories(String keyword) {
        String content = contentGetterSetter.getContentFromHtml("Game.loadCategories", categoriesUrl);
        List<GameBean> list;
        if (!content.contains("失败")) {
            list = getCategoriesFromContent(keyword, content);
            presenter.loadCategoriesSuccess(list);
        } else {
            presenter.loadCategoriesFailure(content);
            Log.e("loadCategories", content);
        }
    }

    public void loadDetail(int id) {
//        Log.e("url", appdetailsUrl + id);
        String content = contentGetterSetter.getContentFromHtml("Game.loadDetail", appdetailsUrl + id);
        GameBean bean;
        if (!content.contains("失败")) {
            bean = getDetailFromContent(id, content);
            presenter.loadDetailSuccess(bean);
        } else {
            presenter.loadDetailFailure(content);
            Log.e("loadDetail", content);
        }
    }

    public List<GameBean> getFeaturedFromContent(String content) {
        try {
            List<GameBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            JSONArray largeCapsules = all.getJSONArray("large_capsules");
            for (int i = 0; i < largeCapsules.length(); i++) {
                JSONObject jbItem = (JSONObject) largeCapsules.get(i);
                GameBean bean = new GameBean();
                bean.setId(jbItem.getInt("id"));
                bean.setIntType(0);
                bean.setName(jbItem.getString("name"));
                bean.setDiscounted(jbItem.getBoolean("discounted"));
                bean.setDiscountPercent(jbItem.getInt("discount_percent"));
                if (!jbItem.get("original_price").toString().equals("null")) {
                    bean.setOriginalPrice(jbItem.getInt("original_price")/100);
                }
                if (!jbItem.get("final_price").toString().equals("null")) {
                    bean.setFinalPrice(jbItem.getInt("final_price")/100);
                }
                bean.setWindowsAvailable(jbItem.getBoolean("windows_available"));
                bean.setMacAvailable(jbItem.getBoolean("mac_available"));
                bean.setLinuxAvailable(jbItem.getBoolean("linux_available"));
                bean.setCurrency(jbItem.getString("currency"));
                bean.setHeaderImage(jbItem.getString("header_image"));
                list.add(bean);
            }
            JSONArray featuredWin = all.getJSONArray("featured_win");
            for (int i = 0; i < featuredWin.length(); i++) {
                JSONObject jbItem = (JSONObject) featuredWin.get(i);
                GameBean bean = new GameBean();
                bean.setId(jbItem.getInt("id"));
                bean.setIntType(0);
                bean.setName(jbItem.getString("name"));
                bean.setDiscounted(jbItem.getBoolean("discounted"));
                bean.setDiscountPercent(jbItem.getInt("discount_percent"));
                if (!jbItem.get("original_price").toString().equals("null")) {
                    bean.setOriginalPrice(jbItem.getInt("original_price")/100);
                }
                if (!jbItem.get("final_price").toString().equals("null")) {
                    bean.setFinalPrice(jbItem.getInt("final_price")/100);
                }
                bean.setWindowsAvailable(jbItem.getBoolean("windows_available"));
                bean.setMacAvailable(jbItem.getBoolean("mac_available"));
                bean.setLinuxAvailable(jbItem.getBoolean("linux_available"));
                bean.setCurrency(jbItem.getString("currency"));
                bean.setHeaderImage(jbItem.getString("header_image"));
                list.add(bean);
            }
            JSONArray featuredMac = all.getJSONArray("featured_mac");
            for (int i = 0; i < featuredMac.length(); i++) {
                JSONObject jbItem = (JSONObject) featuredMac.get(i);
                GameBean bean = new GameBean();
                bean.setId(jbItem.getInt("id"));
                bean.setIntType(0);
                bean.setName(jbItem.getString("name"));
                bean.setDiscounted(jbItem.getBoolean("discounted"));
                bean.setDiscountPercent(jbItem.getInt("discount_percent"));
                if (!jbItem.get("original_price").toString().equals("null")) {
                    bean.setOriginalPrice(jbItem.getInt("original_price")/100);
                }
                if (!jbItem.get("final_price").toString().equals("null")) {
                    bean.setFinalPrice(jbItem.getInt("final_price")/100);
                }
                bean.setWindowsAvailable(jbItem.getBoolean("windows_available"));
                bean.setMacAvailable(jbItem.getBoolean("mac_available"));
                bean.setLinuxAvailable(jbItem.getBoolean("linux_available"));
                bean.setCurrency(jbItem.getString("currency"));
                bean.setHeaderImage(jbItem.getString("header_image"));
                list.add(bean);
            }
            JSONArray featuredLinux = all.getJSONArray("featured_linux");
            for (int i = 0; i < featuredLinux.length(); i++) {
                JSONObject jbItem = (JSONObject) featuredLinux.get(i);
                GameBean bean = new GameBean();
                bean.setId(jbItem.getInt("id"));
                bean.setIntType(0);
                bean.setName(jbItem.getString("name"));
                bean.setDiscounted(jbItem.getBoolean("discounted"));
                bean.setDiscountPercent(jbItem.getInt("discount_percent"));
                if (!jbItem.get("original_price").toString().equals("null")) {
                    bean.setOriginalPrice(jbItem.getInt("original_price")/100);
                }
                if (!jbItem.get("final_price").toString().equals("null")) {
                    bean.setFinalPrice(jbItem.getInt("final_price")/100);
                }
                bean.setWindowsAvailable(jbItem.getBoolean("windows_available"));
                bean.setMacAvailable(jbItem.getBoolean("mac_available"));
                bean.setLinuxAvailable(jbItem.getBoolean("linux_available"));
                bean.setCurrency(jbItem.getString("currency"));
                bean.setHeaderImage(jbItem.getString("header_image"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<GameBean> getCategoriesFromContent(String keyword, String content) {
        try {
            List<GameBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            JSONObject object = all.getJSONObject(keyword);
            JSONArray array = object.getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jbItem = (JSONObject) array.get(i);
                GameBean bean = new GameBean();
                bean.setId(jbItem.getInt("id"));
                bean.setIntType(0);
                bean.setName(jbItem.getString("name"));
                bean.setDiscounted(jbItem.getBoolean("discounted"));
                bean.setDiscountPercent(jbItem.getInt("discount_percent"));
                if (!jbItem.get("original_price").toString().equals("null")) {
                    bean.setOriginalPrice(jbItem.getInt("original_price")/100);
                }
                if (!jbItem.get("final_price").toString().equals("null")) {
                    bean.setFinalPrice(jbItem.getInt("final_price")/100);
                }
                bean.setWindowsAvailable(jbItem.getBoolean("windows_available"));
                bean.setMacAvailable(jbItem.getBoolean("mac_available"));
                bean.setLinuxAvailable(jbItem.getBoolean("linux_available"));
                bean.setCurrency(jbItem.getString("currency"));
                bean.setHeaderImage(jbItem.getString("header_image"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GameBean getDetailFromContent(int id, String content) {
        try {
            GameBean bean = new GameBean();
            JSONObject all = new JSONObject(content);
            all = all.getJSONObject("" + id);
            if (all.getBoolean("success")) {
                JSONObject data = all.getJSONObject("data");
                bean.setIntType(0);
                bean.setName(data.getString("name"));
                bean.setDescription(data.getString("detailed_description"));
                bean.setSupportedLanguages(data.getString("supported_languages"));
                String requirements = "";
                JSONObject pc = data.getJSONObject("pc_requirements");
                if (pc.has("minimum")) {
                    requirements += pc.getString("minimum") + "<br>";
                }
                if (pc.has("recommended")) {
                    requirements += pc.getString("recommended") + "<br>";
                }
//                if (data.getJSONArray("mac_requirements").length() == 0) {
//                    Log.e("mac", "mac_requirements");
//                } else {
//                    Log.e("mac", "mac:" + data.getJSONObject("mac_requirements").toString());
//                    JSONObject mac = data.getJSONObject("mac_requirements");
//                    if (mac.has("minimum")) {
//                        requirements += mac.getString("minimum") + "<br>";
//                    }
//                    if (mac.has("recommended")) {
//                        requirements += mac.getString("recommended") + "<br>";
//                    }
//                }
//                if (data.getJSONArray("linux_requirements").length() == 0) {
//                    Log.e("linux", "linux_requirements");
//                } else {
//                    Log.e("linux", "linux:" + data.getJSONObject("linux_requirements").toString());
//                    JSONObject linux = data.getJSONObject("linux_requirements");
//                    if (linux.has("minimum")) {
//                        requirements += linux.getString("minimum") + "<br>";
//                    }
//                    if (linux.has("recommended")) {
//                        requirements += linux.getString("recommended") + "<br>";
//                    }
//                }
                bean.setRequirements(requirements);
                bean.setDevelopers((String) data.getJSONArray("developers").get(0));
                bean.setPublishers((String) data.getJSONArray("publishers").get(0));
                JSONObject priceOverview = data.getJSONObject("price_overview");
                bean.setCurrency(priceOverview.getString("currency"));
                if (!priceOverview.get("initial").toString().equals("null")) {
                    bean.setOriginalPrice(priceOverview.getInt("initial")/100);
                }
                if (!priceOverview.get("final").toString().equals("null")) {
                    bean.setFinalPrice(priceOverview.getInt("final")/100);
                }
                bean.setDiscountPercent(priceOverview.getInt("discount_percent"));
                JSONArray jshots = data.getJSONArray("screenshots");
                List<String> lshots = new ArrayList<>();
                for (int j = 0; j < jshots.length(); j++) {
                    lshots.add(((JSONObject) jshots.get(j)).getString("path_thumbnail"));
                }
                bean.setScreenshots(lshots);
            }
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
