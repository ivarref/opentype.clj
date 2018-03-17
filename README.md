# opentype.clj

A simple API over [opentype.js](https://opentype.js.org/) for Clojure. Runs on the JVM.

## Installation

Add `[opentype-clj "0.0.3"]` to your dependency vector.

## Usage

```clojure
(use 'opentype-clj.core)

(text "Roboto Black" "Hello, World!" 0 150 72)
;      Font name      text           x   y  font-size
=> {:path-data "M46.65 98.81L46.65 150L34.38 150L34.38 128.63L16.21 128.63L16.21 150L3.87 150L3.87 98.81L16.21 98.81L16.21 119.13L34.38 119.13L34.38 98.81L46.65 98.81ZM72.32 150.70L72.32 150.70L72.32 150.70Q67.68 150.70 64.02 149.23L64.02 149.23L64.02 149.23Q60.36 147.75 57.87 145.22L57.87 145.22L57.87 145.22Q55.37 142.69 54.05 139.35L54.05 139.35L54.05 139.35Q52.73 136.01 52.73 132.25L52.73 132.25L52.73 130.91L52.73 130.91Q52.73 126.76 53.88 123.16L53.88 123.16L53.88 123.16Q55.02 119.55 57.32 116.92L57.32 116.92L57.32 116.92Q59.63 114.28 63.05 112.77L63.05 112.77L63.05 112.77Q66.48 111.26 71.05 111.26L71.05 111.26L71.05 111.26Q75.09 111.26 78.31 112.56L78.31 112.56L78.31 112.56Q81.53 113.86 83.76 116.29L83.76 116.29L83.76 116.29Q85.99 118.71 87.17 122.16L87.17 122.16L87.17 122.16Q88.35 125.60 88.35 129.89L88.35 129.89L88.35 134.71L64.79 134.71L64.79 134.71Q65.43 137.91 67.57 139.73L67.57 139.73L67.57 139.73Q69.71 141.56 73.13 141.56L73.13 141.56L73.13 141.56Q75.55 141.56 77.89 140.65L77.89 140.65L77.89 140.65Q80.23 139.73 81.91 137.63L81.91 137.63L87.33 144.02L87.33 144.02Q86.41 145.29 84.99 146.48L84.99 146.48L84.99 146.48Q83.57 147.68 81.67 148.61L81.67 148.61L81.67 148.61Q79.77 149.54 77.41 150.12L77.41 150.12L77.41 150.12Q75.06 150.70 72.32 150.70ZM70.98 120.40L70.98 120.40L70.98 120.40Q69.47 120.40 68.38 120.93L68.38 120.93L68.38 120.93Q67.29 121.45 66.57 122.37L66.57 122.37L66.57 122.37Q65.85 123.28 65.43 124.55L65.43 124.55L65.43 124.55Q65.00 125.81 64.79 127.29L64.79 127.29L76.75 127.29L76.75 126.38L76.75 126.38Q76.82 123.53 75.30 121.96L75.30 121.96L75.30 121.96Q73.79 120.40 70.98 120.40ZM105.61 96L105.61 150L93.73 150L93.73 96L105.61 96ZM125.40 96L125.40 150L113.52 150L113.52 96L125.40 96ZM131.20 131.37L131.20 130.63L131.20 130.63Q131.20 126.48 132.40 122.93L132.40 122.93L132.40 122.93Q133.59 119.38 135.91 116.78L135.91 116.78L135.91 116.78Q138.23 114.18 141.66 112.72L141.66 112.72L141.66 112.72Q145.09 111.26 149.52 111.26L149.52 111.26L149.52 111.26Q154.02 111.26 157.45 112.72L157.45 112.72L157.45 112.72Q160.88 114.18 163.20 116.78L163.20 116.78L163.20 116.78Q165.52 119.38 166.71 122.93L166.71 122.93L166.71 122.93Q167.91 126.48 167.91 130.63L167.91 130.63L167.91 131.37L167.91 131.37Q167.91 135.55 166.73 139.08L166.73 139.08L166.73 139.08Q165.55 142.62 163.23 145.20L163.23 145.20L163.23 145.20Q160.91 147.79 157.48 149.24L157.48 149.24L157.48 149.24Q154.05 150.70 149.59 150.70L149.59 150.70L149.59 150.70Q145.13 150.70 141.70 149.24L141.70 149.24L141.70 149.24Q138.27 147.79 135.93 145.20L135.93 145.20L135.93 145.20Q133.59 142.62 132.40 139.08L132.40 139.08L132.40 139.08Q131.20 135.55 131.20 131.37L131.20 131.37ZM143.05 130.63L143.05 131.37L143.05 131.37Q143.05 133.51 143.35 135.38L143.35 135.38L143.35 135.38Q143.65 137.24 144.40 138.61L144.40 138.61L144.40 138.61Q145.16 139.98 146.43 140.77L146.43 140.77L146.43 140.77Q147.69 141.56 149.59 141.56L149.59 141.56L149.59 141.56Q151.45 141.56 152.70 140.77L152.70 140.77L152.70 140.77Q153.95 139.98 154.69 138.61L154.69 138.61L154.69 138.61Q155.43 137.24 155.74 135.38L155.74 135.38L155.74 135.38Q156.06 133.51 156.06 131.37L156.06 131.37L156.06 130.63L156.06 130.63Q156.06 128.55 155.76 126.69L155.76 126.69L155.76 126.69Q155.46 124.83 154.71 123.44L154.71 123.44L154.71 123.44Q153.95 122.05 152.68 121.22L152.68 121.22L152.68 121.22Q151.42 120.40 149.52 120.40L149.52 120.40L149.52 120.40Q147.66 120.40 146.41 121.22L146.41 121.22L146.41 121.22Q145.16 122.05 144.42 123.44L144.42 123.44L144.42 123.44Q143.68 124.83 143.37 126.69L143.37 126.69L143.37 126.69Q143.05 128.55 143.05 130.63L143.05 130.63ZM184.57 141.07L184.54 148.59L184.54 148.59Q184.54 150.74 183.97 152.88L183.97 152.88L183.97 152.88Q183.41 155.03 182.43 157.01L182.43 157.01L182.43 157.01Q181.44 159 180.12 160.74L180.12 160.74L180.12 160.74Q178.80 162.48 177.29 163.78L177.29 163.78L171.32 160.97L171.32 160.97Q171.88 159.77 172.53 158.56L172.53 158.56L172.53 158.56Q173.18 157.35 173.74 155.98L173.74 155.98L173.74 155.98Q174.30 154.61 174.69 152.97L174.69 152.97L174.69 152.97Q175.08 151.34 175.08 149.26L175.08 149.26L175.08 141.07L184.57 141.07ZM243.53 98.81L250.66 130.91L256.54 98.81L268.77 98.81L257.91 150L245.11 150L238.25 120.82L231.54 150L218.78 150L207.88 98.81L220.15 98.81L226.02 130.91L233.05 98.81L243.53 98.81ZM270.46 131.37L270.46 130.63L270.46 130.63Q270.46 126.48 271.65 122.93L271.65 122.93L271.65 122.93Q272.85 119.38 275.17 116.78L275.17 116.78L275.17 116.78Q277.49 114.18 280.92 112.72L280.92 112.72L280.92 112.72Q284.34 111.26 288.77 111.26L288.77 111.26L288.77 111.26Q293.27 111.26 296.70 112.72L296.70 112.72L296.70 112.72Q300.13 114.18 302.45 116.78L302.45 116.78L302.45 116.78Q304.77 119.38 305.96 122.93L305.96 122.93L305.96 122.93Q307.16 126.48 307.16 130.63L307.16 130.63L307.16 131.37L307.16 131.37Q307.16 135.55 305.98 139.08L305.98 139.08L305.98 139.08Q304.80 142.62 302.48 145.20L302.48 145.20L302.48 145.20Q300.16 147.79 296.74 149.24L296.74 149.24L296.74 149.24Q293.31 150.70 288.84 150.70L288.84 150.70L288.84 150.70Q284.38 150.70 280.95 149.24L280.95 149.24L280.95 149.24Q277.52 147.79 275.19 145.20L275.19 145.20L275.19 145.20Q272.85 142.62 271.65 139.08L271.65 139.08L271.65 139.08Q270.46 135.55 270.46 131.37L270.46 131.37ZM282.30 130.63L282.30 131.37L282.30 131.37Q282.30 133.51 282.60 135.38L282.60 135.38L282.60 135.38Q282.90 137.24 283.66 138.61L283.66 138.61L283.66 138.61Q284.41 139.98 285.68 140.77L285.68 140.77L285.68 140.77Q286.95 141.56 288.84 141.56L288.84 141.56L288.84 141.56Q290.71 141.56 291.96 140.77L291.96 140.77L291.96 140.77Q293.20 139.98 293.94 138.61L293.94 138.61L293.94 138.61Q294.68 137.24 295.00 135.38L295.00 135.38L295.00 135.38Q295.31 133.51 295.31 131.37L295.31 131.37L295.31 130.63L295.31 130.63Q295.31 128.55 295.01 126.69L295.01 126.69L295.01 126.69Q294.71 124.83 293.96 123.44L293.96 123.44L293.96 123.44Q293.20 122.05 291.94 121.22L291.94 121.22L291.94 121.22Q290.67 120.40 288.77 120.40L288.77 120.40L288.77 120.40Q286.91 120.40 285.66 121.22L285.66 121.22L285.66 121.22Q284.41 122.05 283.68 123.44L283.68 123.44L283.68 123.44Q282.94 124.83 282.62 126.69L282.62 126.69L282.62 126.69Q282.30 128.55 282.30 130.63L282.30 130.63ZM335.60 111.68L335.39 122.68L335.39 122.68Q335.04 122.65 334.53 122.60L334.53 122.60L334.53 122.60Q334.02 122.54 333.47 122.51L333.47 122.51L333.47 122.51Q332.93 122.47 332.40 122.44L332.40 122.44L332.40 122.44Q331.88 122.40 331.49 122.40L331.49 122.40L331.49 122.40Q328.64 122.40 326.88 123.26L326.88 123.26L326.88 123.26Q325.13 124.13 324.32 125.92L324.32 125.92L324.32 150L312.47 150L312.47 111.96L323.58 111.96L323.96 116.85L323.96 116.85Q325.37 114.21 327.46 112.73L327.46 112.73L327.46 112.73Q329.55 111.26 332.30 111.26L332.30 111.26L332.30 111.26Q333.18 111.26 334.04 111.36L334.04 111.36L334.04 111.36Q334.90 111.47 335.60 111.68L335.60 111.68ZM352.16 96L352.16 150L340.28 150L340.28 96L352.16 96ZM357.96 131.44L357.96 130.70L357.96 130.70Q357.96 126.38 358.91 122.79L358.91 122.79L358.91 122.79Q359.86 119.20 361.72 116.64L361.72 116.64L361.72 116.64Q363.59 114.07 366.35 112.66L366.35 112.66L366.35 112.66Q369.11 111.26 372.76 111.26L372.76 111.26L372.76 111.26Q375.47 111.26 377.58 112.31L377.58 112.31L377.58 112.31Q379.69 113.37 381.34 115.27L381.34 115.27L381.34 96L393.22 96L393.22 150L382.57 150L381.97 145.92L381.97 145.92Q380.29 148.14 378 149.42L378 149.42L378 149.42Q375.71 150.70 372.69 150.70L372.69 150.70L372.69 150.70Q369.07 150.70 366.33 149.24L366.33 149.24L366.33 149.24Q363.59 147.79 361.72 145.22L361.72 145.22L361.72 145.22Q359.86 142.65 358.91 139.12L358.91 139.12L358.91 139.12Q357.96 135.59 357.96 131.44L357.96 131.44ZM369.81 130.70L369.81 131.44L369.81 131.44Q369.81 133.58 370.09 135.43L370.09 135.43L370.09 135.43Q370.37 137.27 371.06 138.64L371.06 138.64L371.06 138.64Q371.74 140.02 372.88 140.79L372.88 140.79L372.88 140.79Q374.03 141.56 375.71 141.56L375.71 141.56L375.71 141.56Q377.72 141.56 379.13 140.70L379.13 140.70L379.13 140.70Q380.53 139.84 381.34 138.26L381.34 138.26L381.34 123.77L381.34 123.77Q379.72 120.40 375.79 120.40L375.79 120.40L375.79 120.40Q374.13 120.40 372.99 121.17L372.99 121.17L372.99 121.17Q371.85 121.95 371.14 123.33L371.14 123.33L371.14 123.33Q370.44 124.72 370.13 126.60L370.13 126.60L370.13 126.60Q369.81 128.48 369.81 130.70L369.81 130.70ZM413.23 98.81L411.71 133.79L402.12 133.79L400.61 98.81L413.23 98.81ZM400.25 144.69L400.25 144.69L400.25 144.69Q400.25 143.43 400.73 142.34L400.73 142.34L400.73 142.34Q401.20 141.25 402.08 140.46L402.08 140.46L402.08 140.46Q402.96 139.66 404.16 139.21L404.16 139.21L404.16 139.21Q405.35 138.75 406.83 138.75L406.83 138.75L406.83 138.75Q408.30 138.75 409.50 139.21L409.50 139.21L409.50 139.21Q410.70 139.66 411.57 140.46L411.57 140.46L411.57 140.46Q412.45 141.25 412.93 142.34L412.93 142.34L412.93 142.34Q413.40 143.43 413.40 144.69L413.40 144.69L413.40 144.69Q413.40 145.96 412.93 147.05L412.93 147.05L412.93 147.05Q412.45 148.14 411.57 148.93L411.57 148.93L411.57 148.93Q410.70 149.72 409.50 150.18L409.50 150.18L409.50 150.18Q408.30 150.63 406.83 150.63L406.83 150.63L406.83 150.63Q405.35 150.63 404.16 150.18L404.16 150.18L404.16 150.18Q402.96 149.72 402.08 148.93L402.08 148.93L402.08 148.93Q401.20 148.14 400.73 147.05L400.73 147.05L400.73 147.05Q400.25 145.96 400.25 144.69Z",
    :bounding-box #opentype_clj.wrapper.BoundingBox{:x1 3.8671875, :y1 96.0, :x2 413.40234375, :y2 163.78125},
    :bounding-box-path-data "M3.87 96.00 H413.40 V163.78 H3.87 Z"}

(text->path-data "Roboto Black" "Hello, World!" 0 150 72)
=> "M46.65 98.81L46.65 150L34.38 150L34.38 128.63L16.21 128.63L16.21 150L3.87 150L3.87 98.81L16.21 98.81L16.21 119.13L34.38 119.13L34.38 98.81L46.65 98.81ZM72.32 150.70L72.32 150.70L72.32 150.70Q67.68 150.70 64.02 149.23L64.02 149.23L64.02 149.23Q60.36 147.75 57.87 145.22L57.87 145.22L57.87 145.22Q55.37 142.69 54.05 139.35L54.05 139.35L54.05 139.35Q52.73 136.01 52.73 132.25L52.73 132.25L52.73 130.91L52.73 130.91Q52.73 126.76 53.88 123.16L53.88 123.16L53.88 123.16Q55.02 119.55 57.32 116.92L57.32 116.92L57.32 116.92Q59.63 114.28 63.05 112.77L63.05 112.77L63.05 112.77Q66.48 111.26 71.05 111.26L71.05 111.26L71.05 111.26Q75.09 111.26 78.31 112.56L78.31 112.56L78.31 112.56Q81.53 113.86 83.76 116.29L83.76 116.29L83.76 116.29Q85.99 118.71 87.17 122.16L87.17 122.16L87.17 122.16Q88.35 125.60 88.35 129.89L88.35 129.89L88.35 134.71L64.79 134.71L64.79 134.71Q65.43 137.91 67.57 139.73L67.57 139.73L67.57 139.73Q69.71 141.56 73.13 141.56L73.13 141.56L73.13 141.56Q75.55 141.56 77.89 140.65L77.89 140.65L77.89 140.65Q80.23 139.73 81.91 137.63L81.91 137.63L87.33 144.02L87.33 144.02Q86.41 145.29 84.99 146.48L84.99 146.48L84.99 146.48Q83.57 147.68 81.67 148.61L81.67 148.61L81.67 148.61Q79.77 149.54 77.41 150.12L77.41 150.12L77.41 150.12Q75.06 150.70 72.32 150.70ZM70.98 120.40L70.98 120.40L70.98 120.40Q69.47 120.40 68.38 120.93L68.38 120.93L68.38 120.93Q67.29 121.45 66.57 122.37L66.57 122.37L66.57 122.37Q65.85 123.28 65.43 124.55L65.43 124.55L65.43 124.55Q65.00 125.81 64.79 127.29L64.79 127.29L76.75 127.29L76.75 126.38L76.75 126.38Q76.82 123.53 75.30 121.96L75.30 121.96L75.30 121.96Q73.79 120.40 70.98 120.40ZM105.61 96L105.61 150L93.73 150L93.73 96L105.61 96ZM125.40 96L125.40 150L113.52 150L113.52 96L125.40 96ZM131.20 131.37L131.20 130.63L131.20 130.63Q131.20 126.48 132.40 122.93L132.40 122.93L132.40 122.93Q133.59 119.38 135.91 116.78L135.91 116.78L135.91 116.78Q138.23 114.18 141.66 112.72L141.66 112.72L141.66 112.72Q145.09 111.26 149.52 111.26L149.52 111.26L149.52 111.26Q154.02 111.26 157.45 112.72L157.45 112.72L157.45 112.72Q160.88 114.18 163.20 116.78L163.20 116.78L163.20 116.78Q165.52 119.38 166.71 122.93L166.71 122.93L166.71 122.93Q167.91 126.48 167.91 130.63L167.91 130.63L167.91 131.37L167.91 131.37Q167.91 135.55 166.73 139.08L166.73 139.08L166.73 139.08Q165.55 142.62 163.23 145.20L163.23 145.20L163.23 145.20Q160.91 147.79 157.48 149.24L157.48 149.24L157.48 149.24Q154.05 150.70 149.59 150.70L149.59 150.70L149.59 150.70Q145.13 150.70 141.70 149.24L141.70 149.24L141.70 149.24Q138.27 147.79 135.93 145.20L135.93 145.20L135.93 145.20Q133.59 142.62 132.40 139.08L132.40 139.08L132.40 139.08Q131.20 135.55 131.20 131.37L131.20 131.37ZM143.05 130.63L143.05 131.37L143.05 131.37Q143.05 133.51 143.35 135.38L143.35 135.38L143.35 135.38Q143.65 137.24 144.40 138.61L144.40 138.61L144.40 138.61Q145.16 139.98 146.43 140.77L146.43 140.77L146.43 140.77Q147.69 141.56 149.59 141.56L149.59 141.56L149.59 141.56Q151.45 141.56 152.70 140.77L152.70 140.77L152.70 140.77Q153.95 139.98 154.69 138.61L154.69 138.61L154.69 138.61Q155.43 137.24 155.74 135.38L155.74 135.38L155.74 135.38Q156.06 133.51 156.06 131.37L156.06 131.37L156.06 130.63L156.06 130.63Q156.06 128.55 155.76 126.69L155.76 126.69L155.76 126.69Q155.46 124.83 154.71 123.44L154.71 123.44L154.71 123.44Q153.95 122.05 152.68 121.22L152.68 121.22L152.68 121.22Q151.42 120.40 149.52 120.40L149.52 120.40L149.52 120.40Q147.66 120.40 146.41 121.22L146.41 121.22L146.41 121.22Q145.16 122.05 144.42 123.44L144.42 123.44L144.42 123.44Q143.68 124.83 143.37 126.69L143.37 126.69L143.37 126.69Q143.05 128.55 143.05 130.63L143.05 130.63ZM184.57 141.07L184.54 148.59L184.54 148.59Q184.54 150.74 183.97 152.88L183.97 152.88L183.97 152.88Q183.41 155.03 182.43 157.01L182.43 157.01L182.43 157.01Q181.44 159 180.12 160.74L180.12 160.74L180.12 160.74Q178.80 162.48 177.29 163.78L177.29 163.78L171.32 160.97L171.32 160.97Q171.88 159.77 172.53 158.56L172.53 158.56L172.53 158.56Q173.18 157.35 173.74 155.98L173.74 155.98L173.74 155.98Q174.30 154.61 174.69 152.97L174.69 152.97L174.69 152.97Q175.08 151.34 175.08 149.26L175.08 149.26L175.08 141.07L184.57 141.07ZM243.53 98.81L250.66 130.91L256.54 98.81L268.77 98.81L257.91 150L245.11 150L238.25 120.82L231.54 150L218.78 150L207.88 98.81L220.15 98.81L226.02 130.91L233.05 98.81L243.53 98.81ZM270.46 131.37L270.46 130.63L270.46 130.63Q270.46 126.48 271.65 122.93L271.65 122.93L271.65 122.93Q272.85 119.38 275.17 116.78L275.17 116.78L275.17 116.78Q277.49 114.18 280.92 112.72L280.92 112.72L280.92 112.72Q284.34 111.26 288.77 111.26L288.77 111.26L288.77 111.26Q293.27 111.26 296.70 112.72L296.70 112.72L296.70 112.72Q300.13 114.18 302.45 116.78L302.45 116.78L302.45 116.78Q304.77 119.38 305.96 122.93L305.96 122.93L305.96 122.93Q307.16 126.48 307.16 130.63L307.16 130.63L307.16 131.37L307.16 131.37Q307.16 135.55 305.98 139.08L305.98 139.08L305.98 139.08Q304.80 142.62 302.48 145.20L302.48 145.20L302.48 145.20Q300.16 147.79 296.74 149.24L296.74 149.24L296.74 149.24Q293.31 150.70 288.84 150.70L288.84 150.70L288.84 150.70Q284.38 150.70 280.95 149.24L280.95 149.24L280.95 149.24Q277.52 147.79 275.19 145.20L275.19 145.20L275.19 145.20Q272.85 142.62 271.65 139.08L271.65 139.08L271.65 139.08Q270.46 135.55 270.46 131.37L270.46 131.37ZM282.30 130.63L282.30 131.37L282.30 131.37Q282.30 133.51 282.60 135.38L282.60 135.38L282.60 135.38Q282.90 137.24 283.66 138.61L283.66 138.61L283.66 138.61Q284.41 139.98 285.68 140.77L285.68 140.77L285.68 140.77Q286.95 141.56 288.84 141.56L288.84 141.56L288.84 141.56Q290.71 141.56 291.96 140.77L291.96 140.77L291.96 140.77Q293.20 139.98 293.94 138.61L293.94 138.61L293.94 138.61Q294.68 137.24 295.00 135.38L295.00 135.38L295.00 135.38Q295.31 133.51 295.31 131.37L295.31 131.37L295.31 130.63L295.31 130.63Q295.31 128.55 295.01 126.69L295.01 126.69L295.01 126.69Q294.71 124.83 293.96 123.44L293.96 123.44L293.96 123.44Q293.20 122.05 291.94 121.22L291.94 121.22L291.94 121.22Q290.67 120.40 288.77 120.40L288.77 120.40L288.77 120.40Q286.91 120.40 285.66 121.22L285.66 121.22L285.66 121.22Q284.41 122.05 283.68 123.44L283.68 123.44L283.68 123.44Q282.94 124.83 282.62 126.69L282.62 126.69L282.62 126.69Q282.30 128.55 282.30 130.63L282.30 130.63ZM335.60 111.68L335.39 122.68L335.39 122.68Q335.04 122.65 334.53 122.60L334.53 122.60L334.53 122.60Q334.02 122.54 333.47 122.51L333.47 122.51L333.47 122.51Q332.93 122.47 332.40 122.44L332.40 122.44L332.40 122.44Q331.88 122.40 331.49 122.40L331.49 122.40L331.49 122.40Q328.64 122.40 326.88 123.26L326.88 123.26L326.88 123.26Q325.13 124.13 324.32 125.92L324.32 125.92L324.32 150L312.47 150L312.47 111.96L323.58 111.96L323.96 116.85L323.96 116.85Q325.37 114.21 327.46 112.73L327.46 112.73L327.46 112.73Q329.55 111.26 332.30 111.26L332.30 111.26L332.30 111.26Q333.18 111.26 334.04 111.36L334.04 111.36L334.04 111.36Q334.90 111.47 335.60 111.68L335.60 111.68ZM352.16 96L352.16 150L340.28 150L340.28 96L352.16 96ZM357.96 131.44L357.96 130.70L357.96 130.70Q357.96 126.38 358.91 122.79L358.91 122.79L358.91 122.79Q359.86 119.20 361.72 116.64L361.72 116.64L361.72 116.64Q363.59 114.07 366.35 112.66L366.35 112.66L366.35 112.66Q369.11 111.26 372.76 111.26L372.76 111.26L372.76 111.26Q375.47 111.26 377.58 112.31L377.58 112.31L377.58 112.31Q379.69 113.37 381.34 115.27L381.34 115.27L381.34 96L393.22 96L393.22 150L382.57 150L381.97 145.92L381.97 145.92Q380.29 148.14 378 149.42L378 149.42L378 149.42Q375.71 150.70 372.69 150.70L372.69 150.70L372.69 150.70Q369.07 150.70 366.33 149.24L366.33 149.24L366.33 149.24Q363.59 147.79 361.72 145.22L361.72 145.22L361.72 145.22Q359.86 142.65 358.91 139.12L358.91 139.12L358.91 139.12Q357.96 135.59 357.96 131.44L357.96 131.44ZM369.81 130.70L369.81 131.44L369.81 131.44Q369.81 133.58 370.09 135.43L370.09 135.43L370.09 135.43Q370.37 137.27 371.06 138.64L371.06 138.64L371.06 138.64Q371.74 140.02 372.88 140.79L372.88 140.79L372.88 140.79Q374.03 141.56 375.71 141.56L375.71 141.56L375.71 141.56Q377.72 141.56 379.13 140.70L379.13 140.70L379.13 140.70Q380.53 139.84 381.34 138.26L381.34 138.26L381.34 123.77L381.34 123.77Q379.72 120.40 375.79 120.40L375.79 120.40L375.79 120.40Q374.13 120.40 372.99 121.17L372.99 121.17L372.99 121.17Q371.85 121.95 371.14 123.33L371.14 123.33L371.14 123.33Q370.44 124.72 370.13 126.60L370.13 126.60L370.13 126.60Q369.81 128.48 369.81 130.70L369.81 130.70ZM413.23 98.81L411.71 133.79L402.12 133.79L400.61 98.81L413.23 98.81ZM400.25 144.69L400.25 144.69L400.25 144.69Q400.25 143.43 400.73 142.34L400.73 142.34L400.73 142.34Q401.20 141.25 402.08 140.46L402.08 140.46L402.08 140.46Q402.96 139.66 404.16 139.21L404.16 139.21L404.16 139.21Q405.35 138.75 406.83 138.75L406.83 138.75L406.83 138.75Q408.30 138.75 409.50 139.21L409.50 139.21L409.50 139.21Q410.70 139.66 411.57 140.46L411.57 140.46L411.57 140.46Q412.45 141.25 412.93 142.34L412.93 142.34L412.93 142.34Q413.40 143.43 413.40 144.69L413.40 144.69L413.40 144.69Q413.40 145.96 412.93 147.05L412.93 147.05L412.93 147.05Q412.45 148.14 411.57 148.93L411.57 148.93L411.57 148.93Q410.70 149.72 409.50 150.18L409.50 150.18L409.50 150.18Q408.30 150.63 406.83 150.63L406.83 150.63L406.83 150.63Q405.35 150.63 404.16 150.18L404.16 150.18L404.16 150.18Q402.96 149.72 402.08 148.93L402.08 148.93L402.08 148.93Q401.20 148.14 400.73 147.05L400.73 147.05L400.73 147.05Q400.25 145.96 400.25 144.69Z"

(text->bounding-box "Roboto Black" "Hello, World!" 0 150 72)
=> #opentype_clj.wrapper.BoundingBox{:x1 3.8671875, :y1 96.0, :x2 413.40234375, :y2 163.78125}

(text->bounding-box-path-data "Roboto Black" "Hello, World!" 0 150 72)
=> "M3.87 96.00 H413.40 V163.78 H3.87 Z"

(font "Roboto Black")
=> #opentype_clj.wrapper.Font{:name "Roboto Black",
                              :resource "fonts/Roboto-Black.ttf", ; actual resource used to load this font
                              :units-per-em 2048,
                              :ascender 1900,
                              :descender -500,
                              :font-obj #object[opentype_clj.bootstrap$load_font_stream$fn__15394$fn__15395
                                                0xe6f99a3
                                                "opentype_clj.bootstrap$load_font_stream$fn__15394$fn__15395@e6f99a3"]}

; a slightly more complex example:
(let [txt (text "Roboto Black" "Hello, World!" 10 100 72)
      svg (str "<svg width='450' height='150' xmlns='http://www.w3.org/2000/svg'>\n"
               "<path fill='black' stroke='none' d='" (:path-data txt) "' />\n"
               "<path fill='none' stroke='red' d='" (:bounding-box-path-data txt) "' />\n"
               "</svg>\n")]
  (spit "bounding-box.svg" svg))
; will produce the image below:
```

![bounding-box.svg](bounding-box.png)


### Determining font resource from a string

Most functions in `opentype-clj.core` takes a string font name as an input parameter.
This string is matched to a file or classpath resource by trying `font name`.`[|ttf|woff|otf]`
as well as `font-name`.`[|ttf|woff|otf]`. So for example `Roboto Black` will have the following resource candidates:

```clojure
(use 'opentype-clj.font-cache)

(font-name->candidate-resources "Roboto Black")
=> ["Roboto Black" "Roboto Black.ttf" "Roboto Black.woff" "Roboto Black.otf" "fonts/Roboto Black.ttf" 
    "fonts/Roboto Black.woff" "fonts/Roboto Black.otf" "Roboto-Black" "Roboto-Black.ttf" "Roboto-Black.woff"
    "Roboto-Black.otf" "fonts/Roboto-Black.ttf" "fonts/Roboto-Black.woff" "fonts/Roboto-Black.otf"]
```

The first successful match will be used. 
In the case of this project, `Roboto Black` will ultimately resolve to `fonts/Roboto-Black.ttf`.

### Details

Uses `opentype.js v0.8.0`.

Loading a font is somewhat expensive (about 250 ms on my machine), so they are cached by the library.

## License

Original license from `opentype.js`:

    The MIT License (MIT)
    
    Copyright (c) 2017 Frederik De Bleser
    
    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
    the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
    COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
    IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
    CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Please also see [AUTHORS of opentype.js](https://github.com/nodebox/opentype.js/blob/master/AUTHORS.md).
    
Copyright © 2018 Ivar Refsdal

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
