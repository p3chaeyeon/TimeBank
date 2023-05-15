import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';
import axios from "axios";

import "../../styles/css/Transfer/transfer_account.css";

function TransferAccount() {
    const [account, setAccount] = useState("");
    const [owner, setOwner] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const accessToken = 1;

    const [accountExist, setAccountExist] = useState(false);

    const handleNext =  async () =>{
        await axios.get(PATH.SERVER + `api/v1/bank/account/${account}`, {
            headers:{
            'Authorization':`Bearer ${accessToken}`
            }
        }).
        then(response => {
            console.log(response.data);
            if(response.status === 200) {
                setAccountExist(true);
                navigate("/transfer/amount", {state : {account: account, owner: response.data['ownerName']}});
            }
        }).
        catch(function(error){
            console.clear();
        })

        if(accountExist === false || account ===""){
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
            <button onClick={handleNext} className="nextButton">다음</button>
            <Link to="/main" ><button className="beforeButton">이전</button></Link>
            </div>
        </div>
    );


}

export default TransferAccount;