import axios from "axios";
import { ReactElement } from "react";
import { CustomBtn } from "../commons/btn";
import { useNavigate } from "react-router-dom";

const GetEmail = (): ReactElement => {
  const authToken = localStorage.getItem("token");
  const navigate = useNavigate();

  const fetchEmail = async () => {
    try {
      const response = await axios.get(
        "https://goditerapi.xyz/api/getUserEmail",
        {
          headers: {
            Authorization: `Bearer ${authToken}`,
          },
        }
      );
      alert(response.data);
      console.log(authToken);
    } catch (error) {
      console.error("Error fetching email:", error);
      alert("Failed to fetch the email.");
      navigate("/");
    }
  };

  return <CustomBtn onClick={fetchEmail}>Get Email</CustomBtn>;
};

export default GetEmail;
