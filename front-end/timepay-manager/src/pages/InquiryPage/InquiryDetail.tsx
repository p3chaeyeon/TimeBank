import React from 'react';
import axios from 'axios';
import moment from 'moment';

import { useState, useEffect } from 'react';
import { useLocation  } from 'react-router-dom';
import { Select, Card } from 'antd';

import { PATH } from '../../constants/path';
import './inquiry_detail.css';

type COMMENT = {
  "commentid": string,
  "commentSeq": string,
  "content": string,
  "commentDate": string,
  "replyStatus": string,
  "userId": string,
  "inquiryId": string
}


export function InquiryDetail() {
  const location = useLocation();
  const inquiryId = location.state.inquiryId;
  const inquiryTitle = location.state.inquiryTitle;
  const inquiryContent = location.state.inquiryContent;
  const inquiryDate = location.state.inquiryDate;
  var replyStatus = location.state.replyStatus === 'PENDING' ? '답변 대기' : '답변 완료';

  const [comments, setComments] = useState<COMMENT[]>([]);
  const [inquiryStatus, setInquiryStatus] = useState(replyStatus);
  const [commentDetail, setCommentDetail] = useState('');

  const accessToken = 1;
  const adminId = '10';

  const status = [
    {id:'PENDING', value: '답변 대기',}, 
    {id:'ANSWERED', value: '답변 완료',}, 
  ];

  const getComment = () => {
    axios.get<COMMENT[]>(PATH.SERVER + `api/v1/inquiries/${inquiryId}/comments`, {
        headers:{
        'Authorization':`Bearer ${accessToken}`
        }
    }).then(response => {
        //console.log(response.data);
        setComments(response.data);
    }).catch(function(error){
        console.log(error)
    })
  };

  const postComment = () =>{
    let statchanger;
    if (inquiryStatus === '답변 대기') statchanger = 'PENDING';
    else statchanger = 'ANSWERED';

    if (inquiryStatus !== replyStatus) {
      axios.put(PATH.SERVER + `api/v1/inquiries/${inquiryId}/status`,{
        'status': statchanger
      },{headers:{
        'Authorization': `Bearer ${accessToken}`        
      }}
      ).then(response => {
        //console.log(response);
      }).catch(function(error){
        console.log(error);
      });
      replyStatus = inquiryStatus;
    }

    if (commentDetail !== ''){
      axios.post(PATH.SERVER + `api/v1/inquiries/${inquiryId}/comments`,{
        'content': commentDetail,
      },{headers:{
        'Authorization': `Bearer ${accessToken}` 
      }});
    }
  };

  useEffect(() =>{
    getComment();
  },[]);

  return (
      
    <div className='background'>
      <Card title={inquiryTitle} extra={inquiryDate} className='mainBox'>
          <Card className='detail'>{inquiryContent}</Card>
          {comments.map((comment) => (
              <Card title={comment.userId == adminId ? <p className='fontBlack'>답변</p> : <p className='fontOrange'>문의</p>} 
                    extra={moment(comment.commentDate).format('YYYY-MM-DD HH:mm')}  key={comment.commentid} className='comment'>
                  <p>{"content : " + comment.content}</p>

              </Card>
          ))}
      </Card>
      <div className='registerBox'>
        <textarea onChange={(e)=>{setCommentDetail(e.target.value)}} placeholder='답변 내용을 입력하세요'></textarea>
        <Select options = {status} size = 'small'  dropdownStyle={{textAlign : 'center'}} onChange={(e)=>setInquiryStatus(e)} defaultValue={replyStatus} className="status"></Select>
        <button onClick = {postComment}>답변 등록</button>
      </div>
    </div>
  
  
  
  
  );
}
