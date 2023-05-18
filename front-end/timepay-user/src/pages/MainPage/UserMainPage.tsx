import { useEffect, useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { headerTitleState } from "../../states/uiState";
import IconGear from "../../assets/images/icon-gear.svg";
import Fav from "../../assets/images/fav.svg";
import { PATH } from "../../utils/paths";
import BaseMenu from "../../components/Menu/BaseMenu";
import { Tooltip } from "antd";
import axios from "axios";
import { BankAccountTransaction } from "../../data/BankAccountTransaction";

const UserMainPage = () => {
  const navigate = useNavigate();
  const [accountNumber, setAccountNumber] = useState<string>("");
  const [title, setTitle] = useState<string>("정릉지점");
  const [accountNum, setAccountNum] = useState<string>(
    `계좌번호 ${accountNumber}`
  );
  const [balance, setBalance] = useState<number>(0);
  const [recentRemittanceAccount, setRecentRemittanceAccount] = useState([]);

  async function getUserAccount() {
    try {
      const accessToken = window.localStorage.getItem("access_token");
      await axios({
        method: "GET",
        url: PATH.SERVER + "/api/v1/bank/account",
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }).then((res) => {
        console.log(
          `getUserAccount status code : ${res.status}\ndata : ${res.data}`
        );
        let index = 0;
        res.data.map((account: any) => {
          if (index > 0) return;
          setAccountNumber(account.bankAccountNumber);
          setAccountNum(`계좌번호 ${accountNumber}`);
          setBalance(account.balance);
          index++;
        });
        getRecentRemittanceAccount(accountNumber);
      });
    } catch (e) {
      console.error(e);
      return false;
    }
  }

  async function getRecentRemittanceAccount(
    accountNumber: string
  ): Promise<BankAccountTransaction[]> {
    try {
      const access_token = window.localStorage.getItem("access_token");
      console.log(`${accountNumber}`);
      await axios({
        method: "GET",
        url: PATH.SERVER + `/api/v1/bank/account/transaction/${accountNumber}`,
        headers: {
          Authorization: "Bearer " + access_token,
        },
      }).then((res) => {
        console.log(
          `getRecentRemittanceAccount status code : ${res.status}\nresponse data: ${res.data}`
        );
        setRecentRemittanceAccount(res.data);
      });
    } catch (e) {
      console.error(e);
      return [];
    }
    return [];
  }

  getUserAccount();

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });
  const handleOnClickLinkBtn = useCallback(
    (accountNumber: string) => {
      if (accountNumber == "") navigate(PATH.PASSWORD);
      else navigate(PATH.TRANSFER);
    },
    [navigate]
  );
  const handleOnClickLinkGearBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate]
  );

  return (
    <>
      <div className="main-page">
        <div className="main-header">
          <div className="menu">
            <Tooltip placement="bottom">
              <BaseMenu />
            </Tooltip>
          </div>
          <img
            src={IconGear}
            alt=""
            onClick={() => handleOnClickLinkGearBtn(PATH.PROFILEEDIT)}
          />
        </div>

        <div className="user-account">
          <div className="user-info">
            <div className="title">{title}</div>
            <div className="account-num">
              {accountNumber == "" ? accountNumber : accountNum}
            </div>
            <div className="main-amount">
              {accountNumber == "" ? (
                <span style={{ color: "#787878", paddingLeft: "5px" }}>
                  현재 계좌가 없어요
                </span>
              ) : (
                <span>
                  {balance}
                  <span style={{ color: "#F1AF23", paddingLeft: "5px" }}>
                    TP
                  </span>
                </span>
              )}
            </div>
          </div>

          <div
            className="bottom-btn"
            onClick={() => handleOnClickLinkBtn(accountNumber)}
          >
            {accountNumber == "" ? <div>계좌 생성하기</div> : <div>이체</div>}
          </div>
        </div>

        <div className="recent-list">
          <span className="title">최근 송금한 계좌</span>
          <div style={{ paddingTop: "20px" }}>
            {recentRemittanceAccount.map((transaction: any) => {
              return (
                <>
                  <div className="list">
                    <div style={{ fontSize: "16px" }}>
                      <div style={{ display: "flex" }}>
                        <span style={{ fontWeight: "bold" }}>
                          {transaction.receiverAccountNumber}
                        </span>{" "}
                        님 <br />
                      </div>
                      <span style={{ fontWeight: "bold" }}>계좌번호</span>{" "}
                      <span style={{ color: "#F1AF23" }}>
                        {transaction.senderAccountNumber}
                      </span>
                    </div>
                    <div className="date">{transaction.transactionAt}</div>
                  </div>
                </>
              );
            })}
          </div>
        </div>
      </div>
    </>
  );
};

export default UserMainPage;
