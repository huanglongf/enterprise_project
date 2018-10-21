package loxia.support;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum TransCodeFormatValidatorEnum implements TransCodeFormatValidator {

    EMS("EMS") {
        public boolean validate(String transCode) {
            // log.debug("[EMS]{}", transCode);
            // String regix = "^E[A-Z]{1}[0-9]{9}(C)[A-Z]{1}";
            // return validateByRegix(transCode, regix);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/EMS.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/EMSTemplate.jasper";
        }
    },
    EMS_EYB("EMS_EYB") {
        public boolean validate(String transCode) {
            log.debug("[EMS_EYB]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/EMS.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/EMSTemplate.jasper";
        }
    },
    EMS_GJ("EMS_GJ") {
        public boolean validate(String transCode) {
            log.debug("[EMS_GJ]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/EMS.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/EMSTemplate.jasper";
        }
    },
    SBWL("SBWL") {
        public boolean validate(String transCode) {
            log.debug("[SBWL]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/EMS.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/EMSTemplate.jasper";
        }
    },
    EMSCOD("EMSCOD") {
        public boolean validate(String transCode) {
            log.debug("[EMSCOD]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/EMSCOD.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/EMSCODTemplate.jasper";
        }
    },

    STO("STO") {
        public boolean validate(String transCode) {
            log.debug("[STO]{}", transCode);
            // String regix = "(36|46|88|58|56|38|66)[0-9]{10}";
            // return validateByRegix(transCode, regix);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/STO.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/STOTemplate.jasper";
        }
    },
    SF("SF") {
        public boolean validate(String transCode) {
            log.debug("[SF]{}", transCode);
            // String regix ="(200|102|021|105|101|301|243)[0-9]{9}";
            // String regix = "[1-9]{1}[0-9]{11}";
            // return validateByRegix(transCode, regix);
            return true;
        }

        public String getPrintTemplate() {
            // return "jasperprint/SF.jasper";
            return "jasperprint/SFKD.jasper";
        }

        public String getSinglePrintTemplate() {
            // return "jasperprint/SFTemplate.jasper";
            return "jasperprint/SFKDTemplate.jasper";
        }
    },
    SFCOD("SFCOD") {
        public boolean validate(String transCode) {
            log.debug("[SFCOD]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            // return "jasperprint/SFCOD.jasper";
            return "jasperprint/SFKD.jasper";
        }

        public String getSinglePrintTemplate() {
            // return "jasperprint/SFCODTemplate.jasper";
            return "jasperprint/SFKDTemplate.jasper";
        }
    },
    YTO("YTO") {
        public boolean validate(String transCode) {
            // log.debug("[YTO]{}", transCode);
            // String regix = "(W||1|2|3|4|5|6|7|8|9)[0-9]{9}";
            // return validateByRegix(transCode, regix);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/YTO.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/YTOTemplate.jasper";
        }
    },
    YTO_QY("YTO_QY") {
        public boolean validate(String transCode) {
            log.debug("[YTO_QY]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/YTO.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/YTOTemplate.jasper";
        }
    },
    ZJS("ZJS") {
        public boolean validate(String transCode) {
            log.debug("[ZJS]{}", transCode);
            String regix = "[0-9]{9}[0-6]{1}";
            boolean isValidate = validateByRegix(transCode, regix);
            if (isValidate) {
                String subStr = transCode.substring(0, 9);
                int intValue1 = Integer.valueOf(subStr);
                int intValue2 = Integer.valueOf(transCode.trim().substring(9));
                if (intValue1 % 7 != intValue2) {
                    isValidate = false;
                }
            }
            return isValidate;
        }

        public String getPrintTemplate() {
            return "jasperprint/ZJS.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/ZJSTemplate.jasper";
        }

    },
    GLRS("GLRS") {
        public boolean validate(String transCode) {
            log.debug("[GLRS]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/GLRS.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/GLRSTemplate.jasper";
        }
    },
    JATC("JATC") {
        public boolean validate(String transCode) {
            log.debug("[JATC]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/JATC.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/JATCTemplate.jasper";
        }
    },
    YD("YD") {
        public boolean validate(String transCode) {
            log.debug("[YD]{}", transCode);
            String regix = "[0-9]{13}";
            return validateByRegix(transCode, regix);
        }

        public String getPrintTemplate() {
            return "jasperprint/YD.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/YDTemplate.jasper";
        }
    },
    YD_QY("YD_QY") {
        public boolean validate(String transCode) {
            log.debug("[YD_QY]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/YD.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/YDTemplate.jasper";
        }
    },
    HLWL("HLWL") {
        public boolean validate(String transCode) {
            log.debug("[HLWL]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/HLWL.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/HLWLTemplate.jasper";
        }
    },
    ZTO("ZTO") {
        public boolean validate(String transCode) {
            log.debug("[ZTO]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/ZTO.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/ZTOTemplate.jasper";
        }
    },
    TTKDEX("TTKDEX") {
        public boolean validate(String transCode) {
            log.debug("[TTKDEX]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/TTKDEX.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/TTKDEXTemplate.jasper";
        }
    },
    HTKY("HTKY") {
        public boolean validate(String transCode) {
            log.debug("[HTKY]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/HTKY.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/HTKYTemplate.jasper";
        }
    },
    STARS("STARS") {
        public boolean validate(String transCode) {
            log.debug("[STARS]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/STARS.jasper";
        }

        public String getSinglePrintTemplate() {
            return "jasperprint/STARSTemplate.jasper";
        }
    },
    ESB("ESB") {
        public boolean validate(String transCode) {
            log.debug("[0-9]*", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/LOGISTICS.jasper";
        }

        public String getSinglePrintTemplate() {
            return getPrintTemplate();
        }
    },

    CRE("CRE") {
        public boolean validate(String transCode) {
            log.debug("[ESB]{}", transCode);
            return true;
        }

        public String getPrintTemplate() {
            return "jasperprint/LOGISTICS.jasper";
        }

        public String getSinglePrintTemplate() {
            return getPrintTemplate();
        }
    };

    private static final Logger log = LoggerFactory.getLogger(TransCodeFormatValidatorEnum.class);

    public boolean validateByRegix(String transCode, String regix) {
        Pattern p = Pattern.compile(regix);
        Matcher m = p.matcher(transCode);
        return m.matches();
    }

    private static Map<String, TransCodeFormatValidator> map = null;

    public synchronized static Map<String, TransCodeFormatValidator> validatorMap() {
        if (map == null) {
            map = new HashMap<String, TransCodeFormatValidator>();
            for (TransCodeFormatValidatorEnum validator : TransCodeFormatValidatorEnum.values()) {
                map.put(validator.getValue(), validator);
                if (log.isDebugEnabled()) {
                    log.debug("Current Logistic rule list:");
                    for (String key : map.keySet())
                        log.debug(key);
                }
            }
        }
        return map;
    }

    final String value;

    public String getValue() {
        return value;
    }

    private TransCodeFormatValidatorEnum(String value) {
        this.value = value;
    }
}
