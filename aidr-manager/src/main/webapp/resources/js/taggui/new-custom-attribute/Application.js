Ext.application({
    requires: [
        'Ext.container.Viewport'
    ],

    name: 'TAGGUI.new-custom-attribute',

    controllers: [
        'NewCustomAttributeController'
    ],

    appFolder: BASE_URL + '/resources/js/taggui/new-custom-attribute',

    launch: function () {
        Ext.QuickTips.init();
        Ext.create('Ext.container.Viewport', {
            cls: 'mainWraper',
            layout: 'fit',
            items: [
                {
                    xtype: 'container',
                    autoScroll: true,
                    layout: {
                        type: 'vbox',
                        align: 'center'
                    },
                    items: [
                        {
                            xtype: 'aidr-header'
                        },
                        {
                            xtype: 'new-custom-attribute-view'
                        },
                        {
                            xtype: 'panel',
                            width: 0,
                            border: false,
                            flex: 1,
                            margin: '0 0 25 0'
                        },
                        {
                            xtype: 'aidr-footer'
                        }
                    ]
                }
            ]
        });
    }
});