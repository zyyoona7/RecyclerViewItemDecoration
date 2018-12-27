package com.zyyoona7.demo

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/13.
 */
class DataServer {

    companion object {

        @JvmStatic
        fun createLinearData(size: Int = 30) = createData("这是 LinearLayoutManager ItemDecoration 展示。", size)

        @JvmStatic
        fun createGridData(size: Int = 30) = createData("这是 GridLayoutManager ItemDecoration 展示。", size)

        @JvmStatic
        fun createStaggeredGridData(size: Int = 30) =
            createData("这是 StaggeredGridLayoutManager ItemDecoration 展示。", size)

        private fun createData(text: String, size: Int): List<String> {
            val list = arrayListOf<String>()
            for (i in 0 until size) {
                list.add(text)
            }
            return list
        }

        @JvmStatic
        fun createSectionLinearData(size: Int = 30) = createSectionData(
            "Section Header",
            "这是 LinearLayoutManager ItemDecoration 展示。", size
        )

        @JvmStatic
        fun createSectionGridData(size: Int = 30) = createSectionData(
            "Section Header",
            "这是 GridLayoutManager ItemDecoration 展示。", size
        )

        @JvmStatic
        fun createSectionStaggerGridData(size: Int = 30) = createSectionData(
            "Section Header",
            "这是 StaggeredGridLayoutManager ItemDecoration 展示。", size
        )

        private fun createSectionData(header: String, text: String, size: Int): List<SectionData> {
            val list = arrayListOf<SectionData>()
            for (i in 0 until size) {
                if (i % 5 == 0) {
                    list.add(SectionData(true, header, text))
                } else {
                    list.add(SectionData(false, header, text))
                }
            }
            return list
        }
    }
}