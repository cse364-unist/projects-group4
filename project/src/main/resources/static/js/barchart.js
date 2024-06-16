class barChart {

    constructor() {
        this.data = null;
        this.banAge = [];
        this.banGender = [];
    }

    initData(data) {
        try {
            this.data = data;
            this.drawChart(this.data);
        }
        catch (error) {
            console.error(error);
        };
    }

    changeAge() {
        const value = $(this).val();
        var ages = []
        if (value == "Youth")
            ages = [1, 18];
        else if (value == "Adults")
            ages = [25, 35, 45];
        else
            ages = [50, 56];
        ages.forEach((e) => {
            if (bar.banAge.includes(e))
                bar.banAge = bar.banAge.filter((d) => (d != e));
            else
                bar.banAge.push(e);
        });
        bar.drawChart(bar.data);
    }

    changeGender() {
        const e = $(this).val();
        if (bar.banGender.includes(e))
            bar.banGender = bar.banGender.filter((d) => (d != e));
        else
            bar.banGender.push(e);
        bar.drawChart(bar.data);
    }

    drawChart(data) {
        d3.select("#barchart").select("svg").remove();

        const filteredData = data.filter((d) => !this.banAge.includes(d.age) && !this.banGender.includes(d.gender));

        const scores = [0, 0, 0, 0, 0];
        filteredData.forEach((d) => scores[parseInt(d.score) - 1] += 1)

        // const margin = { top: 5, right: 30, bottom: 50, left: 120 },
        //     width = 800 - margin.left - margin.right,
        //     height = 600 - margin.top - margin.bottom;
        const margin = { top: 5, right: 30, bottom: 50, left: 120 },
            width = 500 - margin.left - margin.right,
            height = 300 - margin.top - margin.bottom;
        const svg = d3.select("#barchart")
            .append("svg")
            .attr('width', width + margin.left + margin.right)
            .attr('height', height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", `translate(${margin.left},${margin.top})`);

        const yMax = d3.max(scores);
        const xScale = d3.scaleBand().domain([1, 2, 3, 4, 5]).range([0, width]).padding(0.2);
        const yScale = d3.scaleLinear().domain([yMax, 0]).range([0, height]);

        const mouseEnter = (actual, i) => {
            console.log(actual, i);
            const y = yScale(i);
            svg.append('line')
                .attr('id', 'limit')
                .attr('x1', 0)
                .attr('y1', y)
                .attr('x2', width)
                .attr('y2', y)
                .attr('stroke', "blue")
                .attr('stroke-width', "4px");
            
            labels.text((d) => parseInt(d) - parseInt(i));
        };

        const mouseLeave = (actual, i) => {
            console.log(actual, i);
            d3.select("#limit").remove();
            labels.text((d) => d);
        };

        var bar = svg.append("g")
            .selectAll("rect")
            .data(scores)
            .enter().append("rect")
                .attr("x", (d, i) => xScale(i + 1))
                .attr("y", (d) => yScale(d))
                .attr("width", xScale.bandwidth())
                .attr("height", (d) => height - yScale(d))
                .attr("fill", "#20C997")
                .on("mouseenter", mouseEnter)
                .on("mouseleave", mouseLeave);

        const labels = svg.selectAll(".text")
            .data(scores)
            .enter().append("text")
                .attr("class", "label h6")
                .attr("x", (d, i) => xScale(i + 1) + xScale.bandwidth() / 2)
                .attr("y", (d) => Math.min(yScale(d) + 15, height - 15))
                // .attr("dy", "10px")
                .attr("text-anchor", "middle")
                .text((d) => d);
        
        svg.append("g")
            .attr("transform", `translate(0,${height})`)
            .call(d3.axisBottom(xScale));
        svg.append("g")
            .call(d3.axisLeft(yScale));

        svg.append('text')
            .attr('class', 'label h5')
            .attr('y', -30)
            .attr('x', -height / 2 - 30)
            .attr('transform', 'rotate(-90)')
            .attr('text-anchor', 'middle')
            .text('Number of Reviews')
    
        svg.append('text')
            .attr('class', 'label h5')
            .attr('x', width / 2 + margin.right)
            .attr('y', height + 30)
            .attr('text-anchor', 'middle')
            .text('Rating');
        const n = scores[0] + scores[1] + scores[2] + scores[3] + scores[4];
        const sm = scores[0] + scores[1]*2 + scores[2]*3 + scores[3]*4 + scores[4]*5;
        const avg = Math.ceil(sm / n * 100) / 100;
        $('#rating').text(`${avg}/5.0 (${n} reviews)`);
    }
}




