import './GoogleTranslationPage.css';
import React from 'react'
import { useState } from 'react';
import axios from 'axios';

export default function GoogleTranslationPage() {

    const [translatedText, setText] = useState("");
    const [inputText, setInputText] = useState("");
    const [inputDest, setInputDest] = useState("en");


    const translation = () => {
        console.log(inputDest)
        axios.get(`/api/translation?sourceText=${inputText}&destLang=${inputDest}`)
            .then((res) => {
                console.log(res.data.data.translations[0].translatedText)
                setText(res.data.data.translations[0].translatedText)

            }).catch(e => {
                console.log(e)
            })
    }

    return (
        <div className="geoDiv">
            <div className="card">
                <h2>Translation</h2>
                    <form onSubmit={(e) => {e.preventDefault(); translation()}}>
                        <input type="text" placeholder="Enter source text" onChange={e => setInputText(e.target.value)} />
                        <label for="cars">Choose destination language:</label>
                        <select name="cars" id="cars" onChange={e => setInputDest(e.target.value)}>
                            <option value="en">English</option>
                            <option value="tr">Turkish</option>
                            <option value="fr">French</option>
                            <option value="es">Spanish</option>
                            <option value="zh">Chinese</option>
                            <option value="da">Danish</option>
                            <option value="nl">Dutch</option>
                            <option value="de">German</option>
                            <option value="ja">Japanese</option>
                            <option value="la">Latin</option>
                            <option value="no">Norwegian</option>
                        </select>
                        <button type="submit">Translate</button>
                    </form>
                <div className="addressDiv">
                    <p>Translated text: {translatedText}</p>
                </div>
            </div>
        </div>

    );
}
