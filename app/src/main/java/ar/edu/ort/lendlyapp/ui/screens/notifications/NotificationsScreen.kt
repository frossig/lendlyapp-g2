package ar.edu.ort.lendlyapp.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.AppTopBar
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.ContentTertiary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private data class NotificationItem(
    val title: String,
    val body: String,
    val date: String,
    val unread: Boolean
)

private data class NotificationGroup(
    val label: String,
    val items: List<NotificationItem>
)

private val sampleBody =
    "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay."

private fun buildGroups(dateLabel: String): List<NotificationGroup> = listOf(
    NotificationGroup(
        label = "Today",
        items = listOf(
            NotificationItem("Your due date is almost here!", sampleBody, dateLabel, unread = true),
            NotificationItem("Your due date is almost here!", sampleBody, dateLabel, unread = true),
            NotificationItem("Got a minute to help us out?", sampleBody, dateLabel, unread = false),
            NotificationItem("Got a minute to help us out?", sampleBody, dateLabel, unread = false)
        )
    ),
    NotificationGroup(
        label = "Announcement",
        items = listOf(
            NotificationItem("Your due date is almost here!", sampleBody, dateLabel, unread = true),
            NotificationItem("Got a minute to help us out?", sampleBody, dateLabel, unread = false)
        )
    )
)

@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    var showCalendar by remember { mutableStateOf(false) }
    val todayLabel = remember {
        LocalDate.now().format(DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH))
    }
    val groups = remember(todayLabel) { buildGroups(todayLabel) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        AppTopBar(
            onBack = onBack,
            trailing = {
                IconButton(onClick = { showCalendar = true }) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "Filter by date",
                        tint = ContentPrimary
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Notification",
                    style = MaterialTheme.typography.headlineLarge,
                    color = ContentPrimary
                )
                Spacer(Modifier.height(20.dp))
            }
            groups.forEach { group ->
                sectionHeader(group.label)
                items(group.items) { NotificationRow(it) }
            }
        }
    }

    if (showCalendar) {
        DueDatesDialog(onDismiss = { showCalendar = false })
    }
}

private fun LazyListScope.sectionHeader(label: String) {
    item {
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = ContentSecondary
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ContentTertiary.copy(alpha = 0.2f))
        )
    }
}

@Composable
private fun NotificationRow(item: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(10.dp)
                .clip(CircleShape)
                .background(
                    if (item.unread) InteractiveAccent
                    else ContentTertiary.copy(alpha = 0.3f)
                )
        )
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = ContentPrimary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = ContentSecondary
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = item.body,
                style = MaterialTheme.typography.bodyMedium,
                color = ContentSecondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DueDatesDialog(onDismiss: () -> Unit) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    val datePickerColors = DatePickerDefaults.colors(
        containerColor = BackgroundElevated,
        titleContentColor = ContentSecondary,
        headlineContentColor = ContentPrimary,
        weekdayContentColor = ContentSecondary,
        subheadContentColor = ContentPrimary,
        yearContentColor = ContentPrimary,
        currentYearContentColor = ContentPrimary,
        selectedYearContentColor = ContentPrimary,
        selectedYearContainerColor = InteractiveAccent,
        dayContentColor = ContentPrimary,
        selectedDayContentColor = ContentPrimary,
        selectedDayContainerColor = InteractiveAccent,
        todayContentColor = ContentPrimary,
        todayDateBorderColor = InteractiveAccent
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK", color = ContentPrimary)
            }
        },
        colors = datePickerColors
    ) {
        DatePicker(
            state = state,
            title = {
                Text(
                    text = "Due dates",
                    style = MaterialTheme.typography.labelLarge,
                    color = ContentSecondary,
                    modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                )
            },
            showModeToggle = false,
            colors = datePickerColors
        )
    }
}
