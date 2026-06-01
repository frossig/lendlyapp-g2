package ar.edu.ort.lendlyapp.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.ui.components.OutlineButton
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.BaseDark
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import ar.edu.ort.lendlyapp.ui.theme.InteractiveSecondary
import kotlinx.coroutines.launch

private data class OnboardingStep(
    val title: String,
    val subtitle: String,
    @DrawableRes val imageRes: Int
)

private val steps = listOf(
    OnboardingStep("QUICK LOANS", "Trusted for easy, fast loan approvals.", R.drawable.img_onboarding_1),
    OnboardingStep("LOAN PRODUCT IN-APP", "Many products to loan.", R.drawable.img_onboarding_2),
    OnboardingStep("TRACK & PAY EASILY", "", R.drawable.img_onboarding_3)
)

@Composable
fun OnboardingScreen(
    onGoToLogin: () -> Unit,
    onGoToRegister: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { steps.size })
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage
    val isLast = currentPage == steps.size - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaseDark),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(56.dp))

        Image(
            painter = painterResource(R.drawable.logo_lendly_small),
            contentDescription = null,
            modifier = Modifier.size(width = 115.dp, height = 40.dp)
        )

        Spacer(Modifier.height(16.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            val step = steps[page]
            OnboardingStepLayout(
                imageRes = step.imageRes,
                title = step.title,
                subtitle = step.subtitle
            )
        }

        Spacer(Modifier.height(20.dp))

        PageIndicator(current = currentPage, total = steps.size)

        Spacer(Modifier.height(20.dp))

        AnimatedContent(
            targetState = isLast,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "buttons"
        ) { last ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                if (last) {
                    OutlineButton(text = "Log In", onClick = onGoToLogin)
                    Spacer(Modifier.height(12.dp))
                    PrimaryButton(text = "Sign up for free", onClick = onGoToRegister)
                } else {
                    PrimaryButton(
                        text = "Get Started",
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentPage + 1)
                            }
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun PageIndicator(current: Int, total: Int) {
    Row(horizontalArrangement = Arrangement.Center) {
        repeat(total) { index ->
            val isActive = index == current
            val width by animateDpAsState(
                targetValue = if (isActive) 24.dp else 8.dp,
                label = "dotWidth"
            )
            val color by animateColorAsState(
                targetValue = if (isActive) InteractiveAccent else InteractiveSecondary,
                label = "dotColor"
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(width = width, height = 8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
