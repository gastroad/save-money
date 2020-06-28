import React from "react";
import Chart from "./Chart";

import { API } from "@components/axios";

const DashBoard: React.FC = (props) => {
  React.useEffect(() => {}, []);
  return (
    <section>
      <h2>DashBoard</h2>
      <article>통계 페이지</article>
      <button>asd</button>
      <div className="graph_wrap">
        <Chart />
      </div>
    </section>
  );
};

export default DashBoard;
