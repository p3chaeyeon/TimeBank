import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect, useCallback, useRef } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { Select, Card } from 'antd';
import axios from "axios";
import "./qna_logmain.css";

type QNA = {
    "inquiryid": string,
    "title": string,
    "content": string,
    "inquiryDate": string,
    "replyStatus": string,
    "userId": string
}


function QnaLogMain() {

    const navigate = useNavigate();

    const [qnaResponse, setQnaResponse] = useState<QNA[]>([]);
    const [selectedTerm, setSelectedTerm] = useState('지난 1개월간');
    const [searchVal, setSearchVal] = useState("");
    const [selectedQna, setSelectedQna] = useState("");
    const [selectedContent, setSelectedContent] = useState("");
    const prevValue = useRef({selectedQna, selectedContent});
    const accessToken = 1;
    const userID = 1;

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

    const getQnas = () => {axios.get<QNA[]>(`http://localhost:8080/api/v1/inquiries/users/${userID}`, {headers:{
        'Authorization':`Bearer ${accessToken}`
      }}).
      then(response => {
            //console.log(response.data);
            setQnaResponse(response.data);
            }).
      catch(function(error){
        console.log(error)})
    };

    const handleCard = useCallback((selectedQna:string) =>{
        //console.log(selectedContent);
        navigate(`/qna/detail/${selectedQna}`, {state:{Qna : selectedQna, Content : selectedContent}});
    },[navigate, selectedQna, selectedContent]
    );

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
        setHeaderTitle("문의 내역");
        getQnas();
        if(selectedQna!==""&&(prevValue.current.selectedQna!==selectedQna && prevValue.current.selectedContent!==selectedContent)){
            handleCard(selectedQna);
        }
      }, [handleCard, selectedQna, selectedContent]);
      

    return(
            <div>

                <div>
                    <div className="filterGrid">
                        <Select options = {terms} size = 'small' dropdownStyle={{textAlign : 'center' }} onChange={(e) => handleSelect} value={selectedTerm} className="selectTerm"></Select>
                        <input onChange={e => setSearchVal(e.target.value)} placeholder="문의 제목 입력" value={searchVal} className="searchBox"></input>
                        <button onClick={handleSearch} className="searchButton">&#128270;</button>
                    </div>

                    <Card className="mainBox">
                        {qnaResponse.map((qna) => (
                            <Card key={qna.inquiryid} onClick={e=>{setSelectedQna(qna.inquiryid);setSelectedContent(qna.content);}} className="clickableDetailBox">
                                <span className="qnaStatus" style={qna.replyStatus === 'PENDING' ?{color : '#C7C7C7'} : {color : '#F1AF23'}}>{qna.replyStatus === 'PENDING' ? "등록완료" : "답변완료"}</span>
                                <span className="qnaTitle">Title : {qna.title}</span>
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