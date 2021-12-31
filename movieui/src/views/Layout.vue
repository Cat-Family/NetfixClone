<template>
  <v-app id="inspire">
    <v-app id="sandbox">
      <v-navigation-drawer
        v-model="primaryDrawer.model"
        :clipped="primaryDrawer.clipped"
        :floating="primaryDrawer.floating"
        :mini-variant="primaryDrawer.mini"
        :permanent="primaryDrawer.type === 'permanent'"
        :temporary="primaryDrawer.type === 'temporary'"
        app
        overflow
      ></v-navigation-drawer>

      <v-app-bar color="blue darken-3" :clipped-left="primaryDrawer.clipped" app>
        <v-app-bar-nav-icon
          v-if="primaryDrawer.type !== 'permanent'"
          @click.stop="primaryDrawer.model = !primaryDrawer.model"
        ></v-app-bar-nav-icon>
        <v-toolbar-title style="width: 300px" class="ml-0 pl-4">
          <span class="hidden-sm-and-down">Google Contacts</span>
        </v-toolbar-title>
        <v-text-field
          flatv-app-bar
          solo-inverted
          hide-details
          prepend-inner-icon="mdi-magnify"
          label="Search"
          class="hidden-sm-and-down"
        ></v-text-field>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>mdi-apps</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>mdi-bell</v-icon>
        </v-btn>
        <v-btn icon large>
          <v-avatar size="32px" item>
            <v-img
              src="https://cdn.vuetifyjs.com/images/logos/logo.svg"
              alt="Vuetify"
            ></v-img
          ></v-avatar>
        </v-btn>
      </v-app-bar>

      <v-main>
        <v-container fluid>
          <v-row align="center" justify="center">
            <v-col cols="10">
              <v-card>
                <v-card-text>
                  <v-row>
                    <v-col cols="12" md="6">
                      <span>Scheme</span>
                      <v-switch
                        v-model="$vuetify.theme.dark"
                        primary
                        label="Dark"
                      ></v-switch>
                    </v-col>
                    <v-col cols="12" md="6">
                      <span>Drawer</span>
                      <v-radio-group v-model="primaryDrawer.type" column>
                        <v-radio
                          v-for="drawer in drawers"
                          :key="drawer"
                          :label="drawer"
                          :value="drawer.toLowerCase()"
                          primary
                        ></v-radio>
                      </v-radio-group>
                      <v-switch
                        v-model="primaryDrawer.clipped"
                        label="Clipped"
                        primary
                      ></v-switch>
                      <v-switch
                        v-model="primaryDrawer.floating"
                        label="Floating"
                        primary
                      ></v-switch>
                      <v-switch
                        v-model="primaryDrawer.mini"
                        label="Mini"
                        primary
                      ></v-switch>
                    </v-col>
                    <v-col cols="12" md="6">
                      <span>Footer</span>
                      <v-switch
                        v-model="footer.inset"
                        label="Inset"
                        primary
                      ></v-switch>
                    </v-col>
                  </v-row>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn text>Cancel</v-btn>
                  <v-btn text color="primary">Submit</v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-main>

      <v-footer :inset="footer.inset" app>
        <span class="px-4">&copy; {{ new Date().getFullYear() }}</span>
      </v-footer>
    </v-app>
  </v-app>
</template>

<script>
import { defineComponent, ref } from "vue";

export default defineComponent({
  setup() {},
  data: () => ({
    drawers: ["Default (no property)", "Permanent", "Temporary"],
    primaryDrawer: {
      model: null,
      type: "default (no property)",
      clipped: false,
      floating: false,
      mini: false,
    },
    footer: {
      inset: false,
    },
  }),
});
</script>
