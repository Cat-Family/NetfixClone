<template>
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
</template>

<script>
import HelloWorld from "../components/HelloWorld.vue";

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
