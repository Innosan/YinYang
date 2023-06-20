package com.example.yinyang.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.components.containers.SectionHeader
import com.example.yinyang.ui.shared.components.misc.ContactItem
import com.example.yinyang.ui.shared.components.misc.Socials
import com.example.yinyang.utils.aboutSocials
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun About() {
    ScreenContainer(
        contentSpacing = 24
    ) {
        SectionHeader(iconId = R.drawable.ic_about, title = R.string.about_screen)

        Text(text = stringResource(id = R.string.about_us_text))

        Socials(
            headingText = R.string.about_social_note,
            socialsArray = aboutSocials,
        )


        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SectionHeader(iconId = R.drawable.ic_contacts, title = R.string.contacts_section)

            ContactItem(
                text = R.string.email,
                icon = R.drawable.ic_contact_mail,
                contentDescription = "Our contact email",
                shape = RoundedCornerShape(10.dp)
            ) {
                
            }

            ContactItem(
                text = R.string.phone,
                icon = R.drawable.ic_phone,
                contentDescription = "Our contact phone",
                shape = RoundedCornerShape(10.dp)
            ) {

            }

            Spacer(modifier = Modifier.size(8.dp))

            ContactItem(
                text = R.string.address,
                icon = R.drawable.ic_order_location,
                contentDescription = "Our contact address",
                shape = RoundedCornerShape(10.dp)
            ) {

            }
        }
        
        Spacer(modifier = Modifier.size(36.dp))

        Text(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    RoundedCornerShape(10.dp)
                )
                .padding(20.dp),

            text = stringResource(id = R.string.help_note),
            fontWeight = FontWeight.ExtraBold
        )
    }
}