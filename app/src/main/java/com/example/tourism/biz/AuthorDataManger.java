package com.example.tourism.biz;

import com.example.tourism.entity.AuthorBean;
import com.example.tourism.entity.TrHeadBean;
import com.example.tourism.utils.CTextUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 抓取攻略库列表信息管理类
 * Name:laodai
 * Time:2019.10.18
 */
public class AuthorDataManger {

    /**
     * 无参构造
     */
    public AuthorDataManger() {
    }

    /**
     * 抓取攻略列表信息
     * @param document
     * @return
     */
    public List<AuthorBean> getAurhorBeans(Document document) {
        List<AuthorBean> beanList = new ArrayList<>();
        Element element = document.select("div.qn-header").first();
        //用户头像地址
        String userPic = element.select("dt.fl").select("img").attr("abs:src");
        //用户名
        String name = element.select("h3.intro-title").select("span.name").text();
        //用户积分
        String score = element.select("h3.intro-title").select("span.score").select("a").text();
        //关注
        String follow = CTextUtils.getBlankSpaces(element.select("div.fr").select("dt").text(), 0);
        //粉丝
        String fans = CTextUtils.getBlankSpaces(element.select("div.fr").select("dt").text(), 1);
        //获取账户详情标题
        Elements elements = element.select("div.nav").select("ul.clrfix").select("li.item");
        AuthorBean authorBean = new AuthorBean();
        TrHeadBean headBean = new TrHeadBean();
        for (Element tMent : elements) {
            String title = tMent.select("a").text();
            String href = tMent.select("a").attr("abs:href");
            headBean.setTitle(title);
            headBean.setHref(href);
        }
        authorBean.setAvatarPic(userPic);
        authorBean.setName(name);
        authorBean.setScore(score);
        authorBean.setFollow(follow);
        authorBean.setFans(fans);
        authorBean.setTrHeadBean(headBean);
        beanList.add(authorBean);
        return beanList;
    }

}
