import React, { useState, useEffect } from "react";
import axios from "axios";
import DeleteIcon from "../icon/DeleteIcon";
import PencilIcon from "../icon/PencilIcon";
import "./Information.css";

interface InformationProps {
  onClose: () => void; // 이 줄을 추가합니다.
}

function Information({ onClose }: InformationProps) {
  const [role, setRole] = useState<string>(""); // 포지션
  const [name, setName] = useState("");
  const [pay, setPay] = useState<string>(""); // 페이

  useEffect(() => {
    axios
      .get(`https://goditerapi.xyz/api/user`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        const { data, code } = response.data;

        // 응답 코드가 200이 아니면 에러 처리
        if (code !== "200") {
          console.error("API response error:", response.data.message);
          return;
        }

        if (
          data &&
          data.role &&
          typeof data.pay === "number" && // 'number' 문자열이 아니라 typeof를 사용합니다.
          data.name
        ) {
          setPay(data.pay);
          setRole(data.role);
          setName(data.name);
        } else {
          console.error("Unexpected API response structure:", data);
        }
      })
      .catch((error) => {
        console.error("API request failed:", error);
      });
  }, []);

  const handleSubmit = () => {
    if (!name.trim()) {
      alert("작업자명을 입력해주세요.");
      return;
    }
    if (!role) {
      alert("포지션을 선택해주세요.");
      return;
    }
    if (!pay) {
      alert("페이를 입력해주세요.");
      return;
    }

    const payload = {
      role,
      pay: parseFloat(pay),
      name,
    };

    axios
      .post(
        `https://goditerapi.xyz/api/signup`,
        payload, // 수정된 페이로드 사용
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        // 성공적으로 작업이 수정된 후, /main으로 리다이렉트
        window.location.href = "/main";
      })
      .catch((error) => {
        console.error("Task update failed:", error);
      });
  };

  return (
    <div className="info-container">
      <button onClick={onClose} className="close-button">
        <DeleteIcon />
      </button>
      <h2 className="info-title">
        <PencilIcon />
        정보 수정
      </h2>

      <div className="scrollable-content">
        <div className="input-group">
          <label className="input-label">작업자명</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="input-field"
          />
        </div>

        <div className="role-label">
          <label>포지션:</label>
        </div>
        <div className="role-selection">
          <button
            type="button"
            className={`role-button EDITOR ${
              role === "EDITOR" ? "selected" : ""
            }`}
            onClick={() => setRole("EDITOR")}
          >
            편집자
          </button>
          <button
            type="button"
            className={`role-button THUMBNAILER ${
              role === "THUMBNAILER" ? "selected" : ""
            }`}
            onClick={() => setRole("THUMBNAILER")}
          >
            썸네일러 / 기타
          </button>
        </div>

        <div className="input-group pay-group">
          <label className="input-label">페이</label>
          <div className="input-with-won-wrapper">
            <input
              type="number"
              value={pay}
              onChange={(e) => setPay(e.target.value)}
              className="input-field input-with-won"
            />
            <span className="won-text">분당 / 개당 (원)</span>
          </div>
        </div>
      </div>

      <button onClick={handleSubmit} className="submit-button">
        정보 수정
      </button>
    </div>
  );
}

export default Information;
