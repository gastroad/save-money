import React from "react";
import * as d3 from "d3";

const data = [4, 8, 15, 16, 23, 42];

const Chart = () => {
  const refSVG = React.useRef<SVGSVGElement | null>(null);
  React.useEffect(() => {
    const d3SVG = d3.select(refSVG.current);
    d3SVG
      .selectAll("rect")
      .data(data)
      .join("rect")
      .attr("y", (d, index) =>
        data.slice(0, index).reduce((sum, datum) => sum + datum * 10 + 10, 0)
      )
      .style("fill", "red")
      .style("width", (d) => `${d * 10}px`)
      .style("height", (d) => `${d * 10}px`)
      .text((d) => d);
  }, []);
  return (
    <div>
      <svg ref={refSVG} style={{ width: "1000px", height: "1000px" }}></svg>
    </div>
  );
};

export default Chart;
