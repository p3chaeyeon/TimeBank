
import { Link } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';
import axios from "axios";

import "./qna_register.css";

function QnaRegister() {
    const [qnaTitle, setQnaTitle] = useState("");
    const [qnaDetail, setQnaDetail] = useState("");
    const accessToken = 1;
    const userId = 1;

    const handleRegister = (e:React.MouseEvent<HTMLButtonElement>) => {
        axios.post(PATH.SERVER + `api/v1/inquiries`, 
        {
            title: qnaTitle,
            content: qnaDetail,
            userId: 1
        },
        {
            headers:{'Authorization':`Bearer ${accessToken}`},

        }).then(function(response){
            console.log(response);
        }).catch(function(error){
            console.log(error);
        })
    }

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
      setHeaderTitle('문의 등록');
    });

    return(

            <div>
                    <input onChange={e => setQnaTitle(e.target.value)} placeholder="문의 제목 입력" className="inputTitle"></input>
                    <textarea onChange={e => setQnaDetail(e.target.value)} placeholder="문의 내용 입력" className="inputContent"></textarea>
                    {/* <input type="image" className="inputImage"></input> */}

                    <Link to="/main"><button onClick={handleRegister} className="registerButton">문의 등록</button></Link>
            </div>

    );


}

export default QnaRegister;

//inputTitle, inputDetail auto Resize방법 찾아서 적용