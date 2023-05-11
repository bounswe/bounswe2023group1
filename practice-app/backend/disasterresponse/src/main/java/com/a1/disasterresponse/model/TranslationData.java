package com.a1.disasterresponse.model;

public class TranslationData {

    private String sourceText;
    private String destLang;
    private String translatedText;

    public TranslationData(String sourceText, String destLang, String translatedText) {
        this.sourceText = sourceText;
        this.destLang = destLang;
        this.translatedText = translatedText;
    }

    public String getSourceText() {
        return this.sourceText;
    }

    public String getDestLang() {
        return this.destLang;
    }

    public String getTranslatedText() {
        return this.translatedText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public void setDestLang(String destLang) {
        this.destLang = destLang;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
    
    
}
