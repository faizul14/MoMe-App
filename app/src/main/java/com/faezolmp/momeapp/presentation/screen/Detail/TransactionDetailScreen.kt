package com.faezolmp.momeapp.presentation.screen.Detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.faezolmp.momeapp.core.utils.formatSignedRupiah
import com.faezolmp.momeapp.presentation.ui.categoryBackgroundOf
import com.faezolmp.momeapp.presentation.ui.categoryColorOf
import com.faezolmp.momeapp.presentation.ui.categoryIconOf
import com.faezolmp.momeapp.presentation.ui.components.MomeCard
import com.faezolmp.momeapp.presentation.ui.components.PrimaryButton
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.DividerColor
import com.faezolmp.momeapp.presentation.ui.theme.ExpenseRed
import com.faezolmp.momeapp.presentation.ui.theme.FieldBg
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconBg
import com.faezolmp.momeapp.presentation.ui.theme.IncomeGreen
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme
import com.faezolmp.momeapp.presentation.ui.theme.TextMuted
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary
import com.faezolmp.momeapp.presentation.ui.theme.WarnBg
import com.faezolmp.momeapp.presentation.ui.theme.WarnTint
import java.io.File

@Composable
fun TransactionDetailScreen(
    state: TransactionDetailUiState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BrandBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Header(onBack = onBack, onDelete = onDelete)
        Spacer(modifier = Modifier.height(20.dp))
        HeroCard(state = state)
        Spacer(modifier = Modifier.height(16.dp))
        DetailCard(state = state)
        val path = state.attachmentPath
        if (state.hasAttachment && path != null) {
            Spacer(modifier = Modifier.height(16.dp))
            AttachmentSection(path = path)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(
                text = "Edit",
                onClick = onEdit,
                icon = Icons.Filled.Edit,
                modifier = Modifier.weight(1f)
            )
            DeleteButton(onClick = onDelete, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun Header(onBack: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Kembali",
            tint = BrandNavy,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBack() }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Detail Transaksi",
            color = BrandNavy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.DeleteOutline,
            contentDescription = "Hapus",
            tint = WarnTint,
            modifier = Modifier
                .size(24.dp)
                .clickable { onDelete() }
        )
    }
}

@Composable
private fun HeroCard(state: TransactionDetailUiState) {
    MomeCard(padding = 24.dp) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(64.dp)
                .clip(CircleShape)
                .background(categoryBackgroundOf(state.colorHex)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = categoryIconOf(state.iconKey),
                contentDescription = null,
                tint = categoryColorOf(state.colorHex),
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = formatSignedRupiah(state.amount, state.isIncome, state.currencySymbol),
            color = if (state.isIncome) IncomeGreen else ExpenseRed,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = state.title,
            color = TextSecondary,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(50))
                .background(if (state.isIncome) FoodIconBg else WarnBg)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = if (state.isIncome) "Pemasukan" else "Pengeluaran",
                color = if (state.isIncome) IncomeGreen else WarnTint,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DetailCard(state: TransactionDetailUiState) {
    MomeCard(padding = 18.dp) {
        DetailRow(label = "Kategori", value = state.categoryName)
        RowDivider()
        DetailRow(label = "Tanggal", value = state.dateLabel)
        RowDivider()
        DetailRow(label = "Waktu", value = state.timeLabel)
        RowDivider()
        DetailRow(label = "Sumber", value = state.source)
        RowDivider()
        DetailRow(label = "Catatan", value = state.note)
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 13.sp,
            modifier = Modifier.width(96.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RowDivider() {
    Divider(color = DividerColor)
}

@Composable
private fun AttachmentSection(path: String) {
    Text(
        text = "Bukti Transaksi",
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp
    )
    Spacer(modifier = Modifier.height(10.dp))
    AsyncImage(
        model = File(path),
        contentDescription = "Bukti transaksi",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(FieldBg)
    )
}

@Composable
private fun DeleteButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, WarnTint, RoundedCornerShape(14.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.DeleteOutline,
            contentDescription = null,
            tint = WarnTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Hapus",
            color = WarnTint,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TransactionDetailScreenPreview() {
    MomeAppTheme {
        TransactionDetailScreen(state = sampleTransactionDetail())
    }
}
