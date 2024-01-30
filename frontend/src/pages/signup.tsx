import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Wave1, Wave2, Wave3 } from "../components/wave";
import "./css/Signup.css";

function Signup() {
  const [name, setName] = useState<string>("");
  const [role, setRole] = useState<string>("");
  const [pay, setPay] = useState<string>("");

  const selectRole = (selectedRole: string) => {
    setRole(selectedRole);
  };

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const apitoken = urlParams.get("token");
    if (apitoken) {
      localStorage.setItem("token", apitoken);
    }
  }, []);

  const authToken = localStorage.getItem("token");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "https://goditerapi.xyz/api/signup",
        {
          name: name,
          role: role,
          pay: pay,
        },
        {
          headers: {
            Authorization: `Bearer ${authToken}`,
          },
        }
      );
      if (response.status === 200) {
        alert("회원가입에 성공했습니다");
        navigate("/main");
      } else {
        alert("회원가입 실패");
      }
    } catch (error) {
      alert("빠트린 항목이 있는지 확인해주세요");
    }
  };

  return (
    <>
      <div className="sign-title-container">
        <div className="sign-sign-title">GO.DITER</div>
        <div className="sign-sign-sub-title">스트리머를 위한 정산 프로그램</div>
      </div>
      <div className="sign-signup-container">
        <form onSubmit={handleSubmit} className="sign-signup-form">
          <label className="sign-label">이름 </label>
          <div className="sign-form-group">
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="이름을 입력해주세요"
            />
          </div>
          <label className="sign-label">포지션 </label>
          <div className="sign-role-selection">
            <button
              type="button"
              className={`sign-role-button EDITOR ${
                role === "EDITOR" ? "selected" : ""
              }`}
              onClick={() => selectRole("EDITOR")}
            >
              편집자
            </button>
            <button
              type="button"
              className={`sign-role-button THUMBNAILER ${
                role === "THUMBNAILER" ? "selected" : ""
              }`}
              onClick={() => selectRole("THUMBNAILER")}
            >
              썸네일러 / 기타
            </button>
          </div>
          <label className="sign-label">페이 </label>
          <div className="sign-form-group">
            <input
              type="text"
              value={pay}
              onChange={(e) => setPay(e.target.value)}
              placeholder="분당 / 개당 (원)"
            />
          </div>
          <div className="sign-form-group">
            <button type="submit" className="sign-submit-button">
              가입하기
            </button>
          </div>
        </form>
        <div className="waves">
          <Wave3 />
          <Wave2 />
          <Wave1 />
        </div>
      </div>
    </>
  );
}

export default Signup;
