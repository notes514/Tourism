package com.example.tourism.biz;

import com.example.tourism.entity.StrategyDetailsBean;
import com.example.tourism.entity.TravelsBean;
import com.example.tourism.utils.CTextUtils;
import com.example.tourism.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 攻略详情管理类
 * Name:laodai
 * Time:2019.10.23
 */
public class StrategyDetailsManger {

    /**
     * 无参构造
     */
    public StrategyDetailsManger() {
    }

    /**
     * 抓取攻略详情页数据信息
     * @param document
     * @return
     */
    public StrategyDetailsBean getStrategyDetailsBeans(Document document) {
        Element element = document.select("div.q_skin").first();
        //获取头部Elements
        Elements beginElements = element.select("div.b_cover").select("img.cover_img");
        //获取背景URL地址
        String backgroundUrl = ImageUtils.getCutImgUrl(beginElements.attr("abs:src"));
        Elements endElements = element.select("div.cover_title");
        //获取攻略详情标题
        String title = endElements.select("div.user_info").select("h1").select("span").text();
        //用户头像URL
        String userPicUrl = ImageUtils.getCutImgUrl(endElements.select("div.container").select("img.user_head").attr("abs:src"));
        //用户主页链接
        String userHref = endElements.select("div.container").select("a.link_userspace").attr("abs:href");
        //用户主页ID
        String strategyId = endElements.select("div.container").select("a.link_userspace").attr("data");
        //文章内容
        String content = element.select("div.b_panel_schedule").select("div.e_main").toString();
        //获取用户信息、点赞评论分享
        Elements coverElements = element.select("div.b_main_info");
        //获取用户信息
        Elements fixElements = coverElements.select("div.fix_box").select("div.e_line2");
        String userName = fixElements.select("li.head").select("a[href]").text();
        String nickName = fixElements.select("li.head").select("a[href]").select("span.user_name_icon").attr("title");
        //创建日期
        String date = CTextUtils.getAutomaticInterceptString(fixElements.select("li.date").select("span").text(), " | ",0);
        //浏览次数
        String browse = CTextUtils.getAutomaticInterceptString(fixElements.select("li.date").select("span").text(), " | ", 2);
        //获取点赞评论分享
        Elements mainElements = element.select("div.main_info_r").select("ul.clrfix").select("li.nav_item");
        //分享数
        String share = mainElements.select("div.sharCT").select("span.num").text();
        //收藏数
        String collection = mainElements.select("div.collectCT").select("span.num").text();
        //评论数
        String comment = mainElements.select("div.commentCT").select("a.sub_item").select("span.num").text();
        //点赞数
        String foubles = mainElements.select("div.likeCT").select("span.num").text();
        Elements topmenuElements = element.select("div.container");
        Elements bElements = topmenuElements.select("div.b_foreword").select("ul.foreword_list").select("li.f_item");
        //获取日期行程数据集
        List<String> forewordList = new ArrayList<>();
        for (Element bMent : bElements) {
            forewordList.add(bMent.select("span.data").text());
        }
        //创建列表信息对象
        TravelsBean travelsBean = new TravelsBean();
        travelsBean.setTitle(title);
        travelsBean.setUserPicUrl(userPicUrl);
        travelsBean.setUserHref(userHref);
        travelsBean.setUserName(userName);
        travelsBean.setNickName(nickName);
        travelsBean.setTime(date);
        travelsBean.setBrowse(browse);
        travelsBean.setFoubles(foubles);
        travelsBean.setComment(comment);
        //创建攻略详情对象
        StrategyDetailsBean detailsBean = new StrategyDetailsBean();
        detailsBean.setStrategyId(strategyId);
        detailsBean.setBackgroundUrl(backgroundUrl);
        detailsBean.setContent(content);
        detailsBean.setShare(share);
        detailsBean.setCollection(collection);
        detailsBean.setForewordList(forewordList);
        detailsBean.setTravelsBean(travelsBean);
        return detailsBean;
    }

}
