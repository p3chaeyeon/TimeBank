import { Link, useLocation } from "react-router-dom";
import { useEffect, useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';


import Card from "antd/es/card/Card";

import "./transfer_account.css";
import "./bgImage.css";
import "./transfer_log.css";

function TransferLog() {
    const location = useLocation();
    const account = location.state.account;
    const amount = location.state.amount;
    const name = location.state.name;
    const date = new Date();
    const datestr = date.getFullYear() + "." + (date.getMonth()+1) + "." + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    const [message, setMessage] = useState("");
    const setHeaderTitle = useSetRecoilState(headerTitleState);

    useEffect(() => {
      setHeaderTitle('이체하기');
    });


    return(
            <div>
                <div>
                <span className="menuInfo">이체 완료</span>
                <Card title="거래내역" className="transferInfo">
                <p>계좌번호 : {account}</p>
                <p>금액 : {amount}</p>
                <p>이름 : {name}</p>
                <p>거래일시 : {datestr}</p>
                <p>{message}</p>
                </Card>
                
                </div>

                <div>
                <Link to="/main"><button className="endButton">완료</button></Link>
                </div>
            </div>
    );


}

export default TransferLog;