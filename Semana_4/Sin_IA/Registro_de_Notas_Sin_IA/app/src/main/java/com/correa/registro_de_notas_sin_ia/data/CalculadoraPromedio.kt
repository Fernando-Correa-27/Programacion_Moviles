package com.correa.registro_de_notas_sin_ia.data

fun calcularPromedioPonderado(
    notaFundamentos: Float,
    notaPOO: Float,
    notaMoviles: Float,
    notaBD: Float
): Double {
    return (notaFundamentos * 0.20) +
            (notaPOO * 0.25) +
            (notaMoviles * 0.30) +
            (notaBD * 0.25)
}