import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.pow


enum class MoviesTabs(val text: String) {
    SHOWING_NOW("Showing Now"),
    UPCOMING("Upcoming")
}

@Composable
fun CustomTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    // Color de la línea indicadora (naranja/rojizo como en la imagen)
    val indicatorColor = Color(0xFFFF5722) // Color naranja

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // TabRow con fondo transparente
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent, // Fondo transparente
            divider = {}, // Sin divisor
            indicator = {} // Sin indicador predeterminado
        ) {
            MoviesTabs.entries.forEachIndexed { index, tab ->
                val selected = selectedTabIndex == index
                val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

                Tab(
                    selected = selected,
                    onClick = { onTabSelected(index) },
                    modifier = Modifier.padding(vertical = 8.dp),
                    interactionSource = remember { MutableInteractionSource() } // Elimina el efecto ripple
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp, top = 8.dp)
                    ) {
                        // Texto
                        Text(
                            text = tab.text,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = fontWeight,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Efecto de difuminado para el tab seleccionado
        // Colocado justo encima de la línea indicadora
        if (selectedTabIndex < MoviesTabs.entries.size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .drawBehind {
                        // Dibujar el difuminado en forma de arco como en la imagen
                        drawArcGlow(
                            tabWidth = size.width / MoviesTabs.entries.size,
                            tabPosition = selectedTabIndex,
                            baseColor = Color.White
                        )
                    }
            )
        }

        // Línea indicadora naranja en la parte inferior
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(2.dp)
                .background(indicatorColor)
        )
    }
}

// Función para dibujar el difuminado en forma de arco
private fun DrawScope.drawArcGlow(
    tabWidth: Float,
    tabPosition: Int,
    baseColor: Color
) {
    val startX = tabWidth * tabPosition
    val centerX = startX + (tabWidth / 2)

    // Altura máxima del arco
    val arcHeight = tabWidth * 0.7f

    // Crear un path con forma de arco semicircular
    val path = Path().apply {
        // Comenzar desde la esquina inferior izquierda del tab
        moveTo(startX, size.height)

        // Dibujar un arco semicircular
        quadraticBezierTo(
            centerX, size.height - arcHeight, // Punto de control (cima del arco)
            startX + tabWidth, size.height    // Punto final (esquina inferior derecha del tab)
        )

        // Cerrar el path
        lineTo(startX, size.height)
    }

    // Dibujar el arco con un degradado radial que se desvanece hacia los bordes
    drawPath(
        path = path,
        brush = Brush.radialGradient(
            colors = listOf(
                baseColor.copy(alpha = 0.15f), // Más visible en el centro
                baseColor.copy(alpha = 0.08f), // Menos visible a medida que se aleja
                baseColor.copy(alpha = 0.03f), // Casi transparente
                Color.Transparent            // Completamente transparente en los bordes
            ),
            center = Offset(centerX, size.height),
            radius = arcHeight * 1.2f
        )
    )
}

