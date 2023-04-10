import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';

import "./transfer_account.css";
import "./bgImage.css";


function TransferPW() {
    const location = useLocation();
    const account = location.state.account;
    const amount = location.state.amount;
    const name = location.state.name;

    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    var passwordCorrect = true;

    const handleNext = (e : React.MouseEvent<HTMLButtonElement>) =>{
        if(password === "" || passwordCorrect === false) {
            setError("비밀번호 오류. 다시 입력해주세요.");
            e.preventDefault();
        }
        else{
            setError("");
            //console.log("correct password");
        }
    };

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
      setHeaderTitle('거래비밀번호 입력');
    });


    return(
            <div>
                
                <div>
                <span className="menuInfo">거래 비밀번호 입력</span>
                <input type='password' onChange={e => setPassword(e.target.value)} placeholder="비밀번호입력" value={password||""} className="inputBox"></input>
                <span className="errorMessage">{error}</span>
                </div>

                <div>
                <Link to="/transfer/amount" state={{account : account}}><button className="beforeButton">이전</button></Link>
                <Link to="/transfer/log" state={{account : account, amount : amount, name : name}}><button onClick={handleNext} className="nextButton">이체</button></Link>
                </div>
            </div>
    );


}

export default TransferPW;