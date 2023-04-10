import { Link } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import "./transfer_account.css";
import "./bgImage.css";

function TransferAcc() {
    const [account, setAccount] = useState("");
    const [error, setError] = useState("");

    var accountExist = false;
    const handleNext = (e : React.MouseEvent<HTMLButtonElement>) =>{
        if(accountExist === false){
            if (account === "123"){
                accountExist = true;
            }
        }

        if(account === "" || accountExist === false) {
            e.preventDefault();
            setError("계좌번호 오류. 다시 입력해주세요.")
        }
        //console.log("clicked : " + account);
        //window.location.href = "/transfer/amount" > Link 태그로 대체
    };

    const setHeaderTitle = useSetRecoilState(headerTitleState);
    useEffect(() => {
      setHeaderTitle('계좌번호 입력');
    });
    
    return(
        <div>
            <div>
            <span className="menuInfo">계좌번호 입력</span>
            <input onChange={e => setAccount(e.target.value)} placeholder="계좌 번호 입력" value={account||""} className="inputBox"></input>
            <span className="errorMessage">{error}</span>
            </div>

            <div>
            <Link to="/transfer/amount" state={{account : account}}><button onClick={handleNext} className="nextButton">다음</button></Link>
            <Link to="/main" ><button className="beforeButton">이전</button></Link>
            </div>
            </div>
    );


}

export default TransferAcc;