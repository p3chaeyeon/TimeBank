import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';

import { Card } from 'antd';
import Modal from "react-modal";

import "./qna_logdetail.css";

function QnaLogDetail() {
    const location = useLocation();

    const id = location.state.Qna;
    const content = location.state.Content;

    const [openModal, setOpenModal] = useState(false);

    const answers = [
        {title : "reply 1", content : "reply1 Content", replyType: "byAdmin", replyID : "1"},
        {title : "reply 2", content : "reply2 Content", replyType: "byUser", replyID : "2"},
        {title : "reply 3", content : "reply3 Content", replyType: "byAdmin", replyID : "3"},
    ];

    const handleModal = () => {
        setOpenModal(true);
    };

    const handleRegister = () => {
        console.log("registered");
    };

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
      setHeaderTitle('문의 상세 내역');
    });

    return(
            <div>

                <div>
                    <Card title={id} className="mainBox">
                        <Card>{content}</Card>
                        {answers.map((answer) => (
                            <Card title={"Reply Title : " + answer.title} extra={<span style={answer.replyType==="byAdmin" ? {color: 'red'} : {color: 'blue'}}>{answer.replyType}</span>} key={answer.replyID} className="detailBox">
                                <p>{"content : " + answer.content}</p>

                            </Card>
                        ))}
                    </Card>
                </div>

                <div>
                    <Link to="/qna/log/main" ><button className="beforeButton">이전</button></Link>
                    <button onClick={handleModal} className="nextButton">추가 문의</button>
                    <Modal isOpen={openModal} onRequestClose={() => setOpenModal(false)} className="modalImage">
                        <div className="inputGrid">
                            {/*<input placeholder="문의 제목 입력" className="inputTitle"></input>*/}
                            <textarea placeholder="문의 내용 입력" className="inputDetail"></textarea>
                        </div>
                        <button onClick={()=>setOpenModal(false)} className="modalClose">&#10005;</button>
                        <button onClick={()=>{setOpenModal(false);handleRegister()}} className="registerModal">등록</button>

                    </Modal>
                </div>
            </div>
        
    );

}

export default QnaLogDetail;