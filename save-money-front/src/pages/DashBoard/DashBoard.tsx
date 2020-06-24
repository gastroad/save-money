import React from "react";
import Chart from "./Chart";

import { API } from "@components/axios";

const DashBoard: React.FC = (props) => {
  console.error(props);
  const test = () => {
    API.get(
      `/token/?id=selectjun3&password=1q2w3e4r!
    `
    ).then((res) => {
      console.log(res);
    });
  };
  React.useEffect(() => {
    test();
  }, []);
  return (
    <section>
      <h2>DashBoard</h2>
      <article>통계 페이지</article>
      <div className="graph_wrap">
        <Chart />
      </div>
    </section>
  );
};

export default DashBoard;
