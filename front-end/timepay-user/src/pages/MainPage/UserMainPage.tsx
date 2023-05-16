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
        "Content-Type": "application/json",
        Authorization: "Bearer " + access_token,
      },
    }).then((res) => {
      console.log(`status code : ${res.status}\nresponse data: ${res.data}`);
      return res.data;
    });
  } catch (e) {
    console.error(e);
    return [];
  }
  return [];
}

const UserMainPage = () => {
  const navigate = useNavigate();
  const accountNumber = window.localStorage.getItem("accountNumber") ?? "";
  const [title, setTitle] = useState<string>("정릉지점");
  const [accountNum, setAccountNum] = useState<string>(
    `계좌번호 ${window.localStorage.getItem("accountNumber")}`
  );
  const [amount, setAmount] = useState<string>(
    `${window.localStorage.getItem("balance")}`
  );

  const recentRemittanceAccount = getRecentRemittanceAccount(accountNumber);

  console.log(`recentRemittanceAccount : ${recentRemittanceAccount}`);

  const sampleData = {
    items: [
      {
        id: "김미영",
        accountNum: "00-00-00",
        date: "2023-04-05",
      },
      {
        id: "박채연",
        accountNum: "00-00-00",
        date: "2023-04-05",
      },
      {
        id: "박보검",
        accountNum: "00-00-00",
        date: "2023-04-05",
      },
      {
        id: "짜장면",
        accountNum: "00-00-00",
        date: "2023-04-05",
      },
      {
        id: "탕수육",
        accountNum: "00-00-00",
        date: "2023-04-05",
      },
    ],
    title: "New",
    longTitle: "New",
    titleId: 3,
    pagingInfo: {
      totalItems: 278,
    },
    status: "Success",
  };

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
                  {amount}
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
            {sampleData.items.map((x) => {
              return (
                <>
                  <div className="list">
                    <div style={{ fontSize: "16px" }}>
                      <div style={{ display: "flex" }}>
                        <span style={{ fontWeight: "bold" }}>{x.id}</span> 님{" "}
                        <br />
                        <img
                          src={Fav}
                          alt=""
                          style={{ position: "absolute", right: "20px" }}
                        />
                      </div>
                      <span style={{ fontWeight: "bold" }}>계좌번호</span>{" "}
                      <span style={{ color: "#F1AF23" }}>{x.accountNum}</span>
                    </div>
                    <div className="date">{x.date}</div>
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
