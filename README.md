#ArrayList modification benchmarks

My results:

|Benchmark               |Mode  |Cnt|Score      |Error    |Units|
|------------------------|------|---|-----------|---------|-----|
|MyBenchmark.add         |avgt  |20 |46709.993  |± 194.636|ns/op|
|MyBenchmark.addAll      |avgt  |20 |50766.345  |± 204.491|ns/op|
|MyBenchmark.addAllOnInit|avgt  |20 |48590.899  |± 187.370|ns/op|
|MyBenchmark.addLoop     |avgt  |20 |35112.950  |±  87.550|ns/op|
|MyBenchmark.addNoMap    |avgt  |20 |35394.578  |±  76.201|ns/op|
|MyBenchmark.addNoStream |avgt  |20 |45372.101  |± 166.558|ns/op|
