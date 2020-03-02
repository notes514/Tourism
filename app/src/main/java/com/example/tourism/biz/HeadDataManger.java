package com.example.tourism.biz;

import com.example.tourism.entity.TrHeadBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 攻略库标签管理类
 */
public class HeadDataManger {

    /**
     * 构造方法
     */
    public HeadDataManger() {
    }

    /**
     * 抓取攻略库Tag信息
     * @param document
     * @return
     */
    public List<TrHeadBean> getTrHeadBeans(Document document) {
        List<TrHeadBean> beanList = new ArrayList<>();
        Element element = document.select("ul.filter_tag").first();
        Elements elements = element.select("a");
        for (Element ment : elements) {
            String title = ment.text();
            String href = ment.attr("abs:href");
            TrHeadBean trHead = new TrHeadBean();
            trHead.setTitle(title);
            trHead.setHref(href);
            beanList.add(trHead);
        }
        return beanList;
    }

}
