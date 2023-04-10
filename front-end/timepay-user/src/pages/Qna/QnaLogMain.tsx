import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect, useCallback, useRef } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { Select, Card } from 'antd';

import "./qna_logmain.css";

function QnaLogMain() {

    const navigate = useNavigate();

    const [selectedTerm, setSelectedTerm] = useState('지난 1개월간');
    const [searchVal, setSearchVal] = useState("");
    const [selectedQna, setSelectedQna] = useState("");
    const [selectedContent, setSelectedContent] = useState("");
    const prevValue = useRef({selectedQna, selectedContent});

    const qnas = [
        {status : "처리완료", title : "QNA 1", content: "content1 for QNA1" , qnaID : "1"},
        {status : "처리중", title : "QNA 2", content: "content2 for QNA2" , qnaID : "2"},
        {status : "미처리", title : "QNA 3", content: "content3 for QNA3" , qnaID : "3"},
        {status : "처리완료", title : "QNA 4", content: "content4 for QNA4" , qnaID : "4"},
        {status : "처리중", title : "QNA 5", content: "content5 for QNA5" , qnaID : "5"},
        {status : "미처리", title : "QNA 6", content: "content6 for QNA6" , qnaID : "6"},
        {status : "처리완료", title : "QNA 7", content: "content7 for QNA7" , qnaID : "7"},
        {status : "처리중", title : "QNA 8", content: "content8 for QNA8" , qnaID : "8"},
        {status : "미처리", title : "QNA 9", content: "content9 for QNA9" , qnaID : "9"},
    ];

    const terms = [
        {id:'1', value: '지난 1개월간',}, {id:'2', value: '지난 3개월간',}, {id:'3', value: '지난 6개월간',}, 
    ];

    const handleSearch = (e:React.MouseEvent<HTMLButtonElement>) =>{
        console.log(selectedTerm+ " " +searchVal)
    };

    const handleSelect = (e:React.ChangeEvent<HTMLInputElement>) =>{
        setSelectedTerm(e.target.value);
        //console.log(e);
    };

    const handleCard = useCallback((selectedQna:string) =>{
        //console.log(selectedContent);
        navigate(`/qna/detail/${selectedQna}`, {state:{Qna : selectedQna, Content : selectedContent}});
    },[navigate, selectedQna, selectedContent]
    );

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
        setHeaderTitle("문의 내역");

        if(selectedQna!==""&&(prevValue.current.selectedQna!==selectedQna && prevValue.current.selectedContent!==selectedContent)){
            handleCard(selectedQna);
        }
      }, [handleCard, selectedQna, selectedContent]);
      //useCallBack 적용 필요
      
    return(
            <div>

                <div>
                    <div className="filterGrid">
                        <Select options = {terms} size = 'small' dropdownStyle={{textAlign : 'center' }} onChange={(e) => handleSelect} value={selectedTerm} className="selectTerm"></Select>
                        <input onChange={e => setSearchVal(e.target.value)} placeholder="문의 제목 입력" value={searchVal} className="searchBox"></input>
                        <button onClick={handleSearch} className="searchButton">&#128270;</button>
                    </div>

                    <Card className="mainBox">
                        {qnas.map((qna) => (
                            <Card key={qna.qnaID} onClick={e=>{setSelectedQna(qna.qnaID);setSelectedContent(qna.content);}} className="clickableDetailBox">
                                <span className="qnaStatus">{"Status : " + qna.status}</span><span className="qnaTitle">{"Title : " + qna.title}</span>
                            </Card>
                        ))}
                    </Card>
                </div>

                <div>
                </div>
            </div>
        
    );


}

export default QnaLogMain;