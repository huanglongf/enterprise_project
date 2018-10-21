package com.jumbo.manager.channel.packingPage;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("packingPageFactory")
public class PackingPageFactoryImpl implements PackingPageFactory {
    @Resource(name = "packingPageDefault")
    private PackingPageInterface ppDefault;
    @Resource(name = "packingPageNike")
    private PackingPageInterface nike;
    @Resource(name = "packingPageMicrosoft")
    private PackingPageInterface microsoft;
    @Resource(name = "packingPageConverse")
    private PackingPageInterface converse;
    @Resource(name = "packingPageCachecache")
    private PackingPageInterface cachecache;
    @Resource(name = "packingPageBonobo")
    private PackingPageInterface bonobo;
    @Resource(name = "packingPagegBonobo")
    private PackingPageInterface gbonobo;
    @Resource(name = "packingPageJackwolfskin")
    private PackingPageInterface jackwolfskin;
    @Resource(name = "packingPageTiger")
    private PackingPageInterface tiger;
    @Resource(name = "packingPageAmazon")
    private PackingPageInterface amazon;
    @Resource(name = "packingPageBurberry")
    private PackingPageBurberry burberry;
    @Resource(name = "packingPageAngfa")
    private PackingPageAngfa angfa;
    @Resource(name = "packingPageCathkidston")
    private PackingPageCathkidston cathkidston;
    @Resource(name = "packingPageNikeHk")
    private PackingPageNikeHk nikehk;
    @Resource(name = "packingPageMicrosoftHk")
    private PackingPageInterface microsofthk;
    @Resource(name = "packingPageItChannel")
    // it天猫制定
    private PackingPageItChannel itChannel;
    @Resource(name = "packingPageNikeO2OQS")
    // nike o2oqs定制
    private PackingPageNikeO2OQS nikeO2OQS;
    @Resource(name = "packingPageColumbia")
    private PackingPageColumbia columbia;
    @Resource(name = "packingPagePuma")
    private PackingPagePuma puma;

    @Resource(name = "packingPagePuma1")
    private PackingPagePuma1 puma1;

    @Resource(name = "packingPagePuma2")
    private PackingPagePuma2 puma2;


    @Resource(name = "packingPagePuma3")
    private PackingPagePuma3 puma3;

    @Resource(name = "packingPagePuma1_1")
    private PackingPagePuma1_1 puma1_1;

    @Resource(name = "packingPagePuma2_2")
    private PackingPagePuma2_2 puma2_2;

    @Resource(name = "packingPagePuma3_3")
    private PackingPagePuma3_3 puma3_3;

    @Resource(name = "packingPagePuma1_1_1")
    private PackingPagePuma1_1_1 puma1_1_1;

    @Resource(name = "packingPagePuma2_2_2")
    private PackingPagePuma2_2_2 puma2_2_2;

    @Resource(name = "packingPagePuma3_3_3")
    private PackingPagePuma3_3_3 puma3_3_3;

    @Resource(name = "packingPageAmericanStandard")
    private PackingPageInterface americanStandard;
    @Resource(name = "packingPageEmporioArmani")
    private PackingPageInterface emporioArmani;
    @Resource(name = "packingPageEsprit")
    private PackingPageInterface esprit;
    @Resource(name = "packingPageStarbucks")
    private PackingPageInterface starbucks;
    @Resource(name = "packingPageCathkidstonHk")
    private PackingPageInterface cathkidstonhk;
    @Resource(name = "packingPageSamsung")
    private PackingPageInterface samsung;
    @Resource(name = "packingPageLoccitane")
    private PackingPageInterface loccitane;
    @Resource(name = "packingPageHknba")
    private PackingPageInterface hknba;
    @Resource(name = "packingPageSunGlassHut")
    private PackingPageInterface sunGlassHut;
    @Resource(name = "packingPageRayBan")
    private PackingPageInterface rayBan;
    @Resource(name = "packingPageClot")
    private PackingPageInterface clot;
    @Resource(name = "packingPageOakley")
    private PackingPageInterface oakley;
    @Resource(name = "packingPageSpeedo")
    private PackingPageInterface speedo;
    @Resource(name = "packingPageCk")
    private PackingPageInterface ck;

    @Resource(name = "packingPageAdidas")
    private PackingPageInterface adidas;


    @Resource(name = "packingPageAdidas2")
    private PackingPageInterface adidas2;


    @Resource(name = "packingPageNikeGw")
    private PackingPageInterface nikegw;

    @Resource(name = "packingPageRg")
    private PackingPageInterface rg;


    @Resource(name = "packingPageMk")
    private PackingPageInterface mk;

    @Resource(name = "packingPageGoWild")
    private PackingPageGoWild packingPageGoWild;

    @Resource(name = "packingPageConverseTM")
    private PackingPageInterface converseTM;

    /**
     * 锐步 TM&JD 定制
     */
    @Resource(name = "packingPageReebokOnline")
    private PackingPageInterface reebokonline;

    /**
     * 锐步 官方旗舰店 定制
     */
    @Resource(name = "packingPageReebokMall")
    private PackingPageInterface reebokmall;
    /**
     * 联合利华 定制
     */
    @Resource(name = "packingPageLhLh")
    private PackingPageInterface lhlh;

    /**
     * GoPro 定制
     */
    @Resource(name = "packingPageGoPro")
    private PackingPageInterface goPro;

    @Resource(name = "packingPageNikePackingList")
    private PackingPageInterface nikePackingList;

    /**
     * Gucci装箱单订制
     */
    @Resource(name = "packingPageGucci")
    private PackingPageInterface gucci;

    /**
     * Ralph Lauren装箱清单订制
     */
    @Resource(name = "packingPageRalphLauren")
    private PackingPageInterface ralphLauren;

    /**
     * Herschel装箱清单订制
     */
    @Resource(name = "packingPageHerschel")
    private PackingPageInterface herschel;

    /**
     * 通用热敏纸装箱清单
     */
    @Resource(name = "packingPageThermal")
    private PackingPageInterface thermal;
    
    
    /**
     * Ralph Lauren装箱清单订制-微信商城
     */
    @Resource(name = "packingPageRalphLaurenWx")
    private PackingPageInterface ralphLaurenWx;

    @Override
    public PackingPageInterface getPackingPage(String type) {
        if (!StringUtils.hasText(type)) {
            return ppDefault;
        } else if (PACKING_PAGE_GO_WILD.equals(type)) {
            return packingPageGoWild;
        } else if (PACKING_PAGE_LHLH.equals(type)) {
            return lhlh;
        } else if (PACKING_PAGE_ESPRIT.equals(type)) {
            return esprit;
        } else if (PACKING_PAGE_NIKE.equals(type)) {
            return nike;
        } else if (PACKING_PAGE_MICROSOFT.equals(type)) {
            return microsoft;
        } else if (PACKING_PAGE_MICROSOFTHK.equals(type)) {
            return microsofthk;
        } else if (PACKING_PAGE_CONVERSE.equals(type)) {
            return converse;
        } else if (PACKING_PAGE_CACHECACHE.equals(type)) {
            return cachecache;
        } else if (PACKING_PAGE_BONOBO.equals(type)) {
            return bonobo;
        } else if (PACKING_PAGE_GBONOBO.equals(type)) {
            return gbonobo;
        } else if (PACKING_PAGE_JACKWOLFSKIN.equals(type)) {
            return jackwolfskin;
        } else if (PACKING_PAGE_TIGER.equals(type)) {
            return tiger;
        } else if (PACKING_PAGE_AMAZON.equals(type)) {
            return amazon;
        } else if (PACKING_PAGE_BURBERRY.equals(type)) {
            return burberry;
        } else if (PACKING_PAGE_ANGFA.equals(type)) {
            return angfa;
        } else if (PACKING_PAGE_CATHKIDSTON.equals(type)) {
            return cathkidston;
        } else if (PACKING_PAGE_NIKEHK.equals(type)) {
            return nikehk;
        } else if (PACKING_PAGE_ITCHANNEL.equals(type)) {
            return itChannel;
        } else if (PACKING_PAGE_NIKE_O2OQS.equals(type)) {
            return nikeO2OQS;
        } else if (PACKING_PAGE_COLUMBIA.equals(type)) {
            return columbia;
        } else if (PACKING_PAGE_PUMA.equals(type)) {
            return puma;
        } else if (PACKING_PAGE_PUMA1.equals(type)) {
            return puma1;
        } else if (PACKING_PAGE_PUMA2.equals(type)) {
            return puma2;
        } else if (PACKING_PAGE_PUMA3.equals(type)) {
            return puma3;
        } else if (PACKING_PAGE_PUMA1_1.equals(type)) {
            return puma1_1;
        } else if (PACKING_PAGE_PUMA2_2.equals(type)) {
            return puma2_2;
        } else if (PACKING_PAGE_PUMA3_3.equals(type)) {
            return puma3_3;
        } else if (PACKING_PAGE_PUMA1_1_1.equals(type)) {
            return puma1_1_1;
        } else if (PACKING_PAGE_PUMA2_2_2.equals(type)) {
            return puma2_2_2;
        } else if (PACKING_PAGE_PUMA3_3_3.equals(type)) {
            return puma3_3_3;
        } else if (PACKING_PAGE_AMERICANSTANDARD.equals(type)) {
            return americanStandard;
        } else if (PACKING_PAGE_EMPORIOARMANI.equals(type)) {
            return emporioArmani;
        } else if (PACKING_PAGE_STARBUCKS.equals(type)) {
            return starbucks;
        } else if (PACKING_PAGE_CATHKIDSTONHK.equals(type)) {
            return cathkidstonhk;
        } else if (PACKING_PAGE_SAMSUNG.equals(type)) {
            return samsung;
        } else if (PACKING_PAGE_LOCCITANE.equals(type)) {
            return loccitane;
        } else if (PACKING_PAGE_HKNBA.equals(type)) {
            return hknba;
        } else if (PACKING_PAGE_SUNGLASSHUT.equals(type)) {
            return sunGlassHut;
        } else if (PACKING_PAGE_RAYBAN.equals(type)) {
            return rayBan;
        } else if (PACKING_PAGE_CLOT.equals(type)) {
            return clot;
        } else if (PACKING_PAGE_OAKLEY.equals(type)) {
            return oakley;
        } else if (PACKING_PAGE_ADIDAS.equals(type)) {
            return adidas;
        } else if (PACKING_PAGE_ADIDAS2.equals(type)) {
            return adidas2;
        } else if (PACKING_PAGE_SPEEDO.equals(type)) {
            return speedo;
        } else if (PACKING_PAGE_CK.equals(type)) {
            return ck;
        } else if (PACKING_PAGE_NIKE_GW.equals(type)) {
            return nikegw;
        } else if (PACKING_PAGE_ROGER_GALLET.equals(type)) {
            return rg;
        } else if (PACKING_PAGE_MK.equals(type)) {
            return mk;
        } else if (PACKING_PAGE_CONVERSE_TM.equals(type)) {
            return converseTM;
        } else if (PACKING_PAGE_REEBOK_MALL.equals(type)) {
            return reebokmall;
        } else if (PACKING_PAGE_REEBOK_ONLINE.equals(type)) {
            return reebokonline;
        } else if (PACKING_PAGE_GOPRO.equals(type)) {
            return goPro;
        } else if (PACKING_PAGE_NIKE_PACKING_LIST.equals(type)) {
            return nikePackingList;
        } else if (PACKING_PAGE_GUCCI.equals(type)) {
            return gucci;
        } else if (PACKING_PAGE_RALPH_LAUREN.equals(type)) {
            return ralphLauren;
        } else if (PACKING_PAGE_HERSCHEL.equals(type)) {
            return herschel;
        } else if (PACKING_PAGE_THERMAL.equals(type)) {
            return thermal;
        }else if(PACKING_PAGE_RALPH_LAUREN_WX.equals(type)){
            return ralphLaurenWx;
        }
        return null;
    }
}
