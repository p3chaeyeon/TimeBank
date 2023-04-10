
import { Link } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';


import "./qna_register.css";

function QnaRegister() {
    const [qnaTitle, setQnaTitle] = useState("");
    const [qnaDetail, setQnaDetail] = useState("");

    const handleRegister = (e:React.MouseEvent<HTMLButtonElement>) => {
        console.log(qnaTitle + " " + qnaDetail)
    }

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
      setHeaderTitle('문의 등록');
    });

    return(
            <div>
                
                <div className="inputGrid">
                    <input onChange={e => setQnaTitle(e.target.value)} placeholder="문의 제목 입력" className="inputTitle"></input>
                    <textarea onChange={e => setQnaDetail(e.target.value)} placeholder="문의 내용 입력" className="inputDetail"></textarea>
                    {/* <input type="image" className="inputImage"></input> */}
                </div>

                <div>
                    <button onClick={handleRegister} className="registerButton">문의 등록</button>
                </div>
            </div>
    );


}

export default QnaRegister;

//inputTitle, inputDetail auto Resize방법 찾아서 적용