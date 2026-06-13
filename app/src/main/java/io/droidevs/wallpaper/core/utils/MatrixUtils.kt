package io.droidevs.wallpaper.core.utils

object MatrixUtils {

    /**
     * Multiplies two color matrices and stores the result in the provided result array.
     *
     * @param m1 The first color matrix as a float array.
     * @param m2 The second color matrix as a float array.
     * @param result The array to store the result of the multiplication.
     */
    fun multiplyMatrices(m1: FloatArray, m2: FloatArray, result: FloatArray) {
        for (i in 0..19) {
            val row = i / 5
            val col = i % 5
            // Perform the matrix multiplication and store the result
            result[i] = m1[row * 5] * m2[col] +
                    m1[row * 5 + 1] * m2[col + 5] +
                    m1[row * 5 + 2] * m2[col + 10] +
                    m1[row * 5 + 3] * m2[col + 15] +
                    m1[row * 5 + 4] * m2[col + 4]
        }
    }

}