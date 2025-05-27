package com.rensystem.p3_cinecode.core.shareComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rensystem.p3_cinecode.core.utils.FilterOption
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors
@Composable
fun <T> FilterOptionsRow(
    filters: List<T>,
    modifier: Modifier = Modifier,
    onClick: (T) -> Unit
) where T : Enum<T>, T : FilterOption {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .background(Color(0xFF141623)),
            .background(Color(0xFF1D1D1D)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        filters.forEachIndexed { index, filter ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
                    .clickable { onClick(filter) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = filter.icon,
                    contentDescription = filter.text,
                    tint = LocalBrandColors.current.textFixed,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = filter.text,
                    color = LocalBrandColors.current.textFixed,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            if (index != filters.lastIndex) {
                Box(
                    modifier = Modifier
                        .heightIn(75.dp)
                        .width(1.dp)
                        .background(LocalBrandColors.current.textFixed.copy(alpha = 0.5f))
                )
            }
        }
    }
}
