import { useEffect, useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import IconGear from '../../assets/images/icon-gear.svg';
import MenuBar from '../../assets/images/menu_bar.svg';
import Fav from '../../assets/images/fav.svg';
import { PATH } from '../../utils/paths';

const UserMainPage = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState<String>("정릉지점");
  const [accountNum, setAccountNum] = useState<String>("계좌번호 000-000-000000");
  const [amount, setAmount] = useState<String>("000,000");

  const sampleData = {
    "items": [
        {
            "id": "김미영",
            "accountNum": "000-000-000000",
            "date": "2023-04-05"
        },
        {
            "id": "박채연",
            "accountNum": "000-000-000000",
            "date": "2023-04-05"
        },
        {
            "id": "박보검",
            "accountNum": "000-000-000000",
            "date": "2023-04-05"
        },
        {
            "id": "ㅁㄴㅇ",
            "accountNum": "000-000-000000",
            "date": "2023-04-05"
        },
        {
            "id": "ㄹㅇ",
            "accountNum": "000-000-000000",
            "date": "2023-04-05"
        }
    ],
    "title": "New",
    "longTitle": "New",
    "titleId": 3,
    "pagingInfo": {
        "totalItems": 278
    },
    "status": "Success"
}

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });
  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <>
      <div className = "main-page">
        <div className= 'main-header'>
          <div className='menu'>
            <img src={MenuBar} alt="" onClick={()=>handleOnClickLinkBtn(PATH.QNAMAIN)}/>
            메뉴
          </div>
          <div><img src={IconGear} alt=""/></div>
        </div>

        <div className='user-account'>
          <div className='user-info'>
            <div className='title'>
              {title}
            </div>
            <div className='account-num'>
              {accountNum}
            </div>
            <div className='main-amount'>
              {amount}<span style={{color:"#F1AF23", paddingLeft:"5px"}}>TP</span>
            </div>
          </div>

          <div className='bottom-btn' onClick = { ()=>handleOnClickLinkBtn(PATH.TRANSFER)}>
            이체
          </div>
        </div>

        <div className='recent-list'>
          <span className='title'>최근 송금한 계좌</span>
            <div style={{paddingTop:"20px"}}>
              {sampleData.items.map(x=>{
                return(
                  <>
                  <div className='list'>
                    <div style={{fontSize:"16px"}}>
                      <div style={{display:"flex"}}>
                        <span style={{fontWeight:"bold"}}>{x.id}</span> 님 <br/>
                        <img src={Fav} alt="" style={{position:"absolute", right:"20px"}}/>
                      </div>
                      <span style={{fontWeight:"bold"}}>계좌번호</span> <span style={{color:"#F1AF23"}}>{x.accountNum}</span>
                    </div>
                    <div className='date'>{x.date}</div>
                  </div>
                  </>
                )
              })}
            </div>
        </div>
      </div>
    </>
  );
};

export default UserMainPage;
