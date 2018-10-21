package com.jumbo.wms.manager.vmi;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.wms.manager.BaseManagerImpl;

public class VmiFactoryImpl extends BaseManagerImpl implements VmiFactory {

    private static final long serialVersionUID = 1435360026668794665L;
    private VmiInterface it;
    private VmiInterface esprit;
    private VmiInterface nike;
    private VmiInterface converse;
    private VmiInterface vmiLevis;
    private VmiInterface coach;
    private VmiInterface cache;
    private VmiInterface follie;
    private VmiInterface aeo;
    private VmiInterface cacheBNB;
    private VmiInterface gymboree;
    private VmiInterface af;
    private VmiInterface converseYx;
    private VmiInterface cathkidston;
    private VmiInterface puma;
    private VmiInterface vmiDefault;
    private VmiInterface guess;
    private VmiInterface ec;
    private VmiInterface gucci;
	private VmiInterface levisChildren;
    private VmiInterface nike2;// 转店退大仓定制



    @Transactional
    public VmiInterface getBrandVmi(String vmiCode) {
        if (!StringUtils.hasText(vmiCode)) {
            return null;
        }
        for (String code : ec.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return ec;
            }
        }


        for (String code : puma.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return puma;
            }
        }


        for (String code : it.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return it;
            }
        }
        for (String code : esprit.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return esprit;
            }
        }
        for (String code : cathkidston.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return cathkidston;
            }
        }
        for (String code : nike.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return nike;
            }
        }
        for (String code : nike2.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return nike2;
            }
        }
        for (String code : converse.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return converse;
            }
        }
        /*
         * for (String code : etam.getVmiCode()) { if (vmiCode.equals(code)) { return etam; } }
         */
        for (String code : vmiLevis.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return vmiLevis;
            }
        }
        for (String code : coach.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return coach;
            }
        }
        for (String code : cache.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return cache;
            }
        }
        for (String code : follie.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return follie;
            }
        }
        for (String code : aeo.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return aeo;
            }
        }

        for (String code : cacheBNB.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return cacheBNB;
            }
        }
        for (String code : gymboree.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return gymboree;
            }
        }
        for (String code : af.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return af;
            }
        }
        for (String code : converseYx.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return converseYx;
            }
        }
        for (String code : guess.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return guess;
            }
        }
        for (String code : gucci.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return gucci;
            }
        }
        for (String code : levisChildren.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return levisChildren;
            }
        }
        return vmiDefault;
    }


    public VmiInterface getGucci() {
        return gucci;
    }


    public void setGucci(VmiInterface gucci) {
        this.gucci = gucci;
    }


    public VmiInterface getEc() {
        return ec;
    }



    public void setEc(VmiInterface ec) {
        this.ec = ec;
    }



    public VmiInterface getGuess() {
        return guess;
    }

    public void setGuess(VmiInterface guess) {
        this.guess = guess;
    }

    public VmiInterface getIt() {
        return it;
    }

    public void setIt(VmiInterface it) {
        this.it = it;
    }

    public VmiInterface getEsprit() {
        return esprit;
    }

    public void setEsprit(VmiInterface esprit) {
        this.esprit = esprit;
    }

    public VmiInterface getNike() {
        return nike;
    }

    public VmiInterface getNike2() {
        return nike2;
    }


    public void setNike2(VmiInterface nike2) {
        this.nike2 = nike2;
    }


    public void setNike(VmiInterface nike) {
        this.nike = nike;
    }

    public VmiInterface getConverse() {
        return converse;
    }

    public void setConverse(VmiInterface converse) {
        this.converse = converse;
    }

    public VmiInterface getVmiLevis() {
        return vmiLevis;
    }

    public void setVmiLevis(VmiInterface vmiLevis) {
        this.vmiLevis = vmiLevis;
    }

    public VmiInterface getCoach() {
        return coach;
    }

    public void setCoach(VmiInterface coach) {
        this.coach = coach;
    }

    public VmiInterface getCache() {
        return cache;
    }

    public void setCache(VmiInterface cache) {
        this.cache = cache;
    }

    public VmiInterface getFollie() {
        return follie;
    }

    public void setFollie(VmiInterface follie) {
        this.follie = follie;
    }

    public VmiInterface getAeo() {
        return aeo;
    }

    public void setAeo(VmiInterface aeo) {
        this.aeo = aeo;
    }


    public VmiInterface getCacheBNB() {
        return cacheBNB;
    }

    public void setCacheBNB(VmiInterface cacheBNB) {
        this.cacheBNB = cacheBNB;
    }

    public VmiInterface getGymboree() {
        return gymboree;
    }

    public void setGymboree(VmiInterface gymboree) {
        this.gymboree = gymboree;
    }

    public VmiInterface getAf() {
        return af;
    }

    public void setAf(VmiInterface af) {
        this.af = af;
    }

    public VmiInterface getConverseYx() {
        return converseYx;
    }

    public void setConverseYx(VmiInterface converseYx) {
        this.converseYx = converseYx;
    }

    public VmiInterface getCathkidston() {
        return cathkidston;
    }

    public void setCathkidston(VmiInterface cathkidston) {
        this.cathkidston = cathkidston;
    }

    public VmiInterface getVmiDefault() {
        return vmiDefault;
    }

    public void setVmiDefault(VmiInterface vmiDefault) {
        this.vmiDefault = vmiDefault;
    }

    public VmiInterface getLevisChildren() {
        return levisChildren;
    }

    public void setLevisChildren(VmiInterface levisChildren) {
        this.levisChildren = levisChildren;
    }


    public VmiInterface getPuma() {
        return puma;
    }


    public void setPuma(VmiInterface puma) {
        this.puma = puma;
    }

}
