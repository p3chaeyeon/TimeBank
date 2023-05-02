import { useEffect, useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import Profile from '../../assets/images/profile.svg';
import BackBtn from '../../assets/images/BackBtn.svg';
import { BottomSheet } from 'react-spring-bottom-sheet'

const ProfileEdit = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState<String>("정릉지점");
    const [accountNum, setAccountNum] = useState<String>("계좌번호 000-000-000000");
    const [amount, setAmount] = useState<String>("000,000");
    const [menuOpen, setMenuOpen] = useState<Boolean>(false);
    const [open, setOpen] = useState(false);


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
            <div className="unregist-page">
                <div className='main-header'>
                    <img src={BackBtn} alt="" style={{ float: "left" }} />
                    내정보 수정
                </div>
                <div className='edit-body'>
                    <div className='user-title'>
                        <div className='circle-profile'>
                            <img src={Profile} alt="" style={{top:"3px", position:"relative"}}/>
                        </div>
                        <div className='name-title'>
                            홍길동<br/>
                            <span style={{color:"#cdcdcd", fontSize:"16px"}}>
                                #123456789
                            </span>
                        </div>
                    </div>

                    <div className='user-info'>
                        <div className='name'>이름 <input type="text" placeholder='이름'/></div>
                        <div className='gender'>성별 <input type="text" placeholder='성별'/></div>
                        <div className='age'>나이 <input type="text" placeholder='나이'/></div>
                        <div className='region'>지역정보 <input type="text" placeholder='지역정보' style={{marginLeft:"18px"}}/></div>
                    </div>

                    <div className='user-account'>
                        <div>
                            계좌번호
                            <span style={{fontSize:"16px", color:"#787878", marginLeft:"10px"}}>000-000-0000</span>
                            <span style={{float:"right", fontSize:"13px", color:"#F1AF23"}} onClick={() => setOpen(true)}>비밀번호 변경</span>
                        </div>
                    </div>
                </div>
                <div className='finish-btn' style={{textAlign:"center"}}>
                    <button style={{marginTop:"50%"}}>수정완료</button>
                </div>
            </div>
            <BottomSheet open={open}>
                
            </BottomSheet>
        </>
    );
};

export default ProfileEdit;
