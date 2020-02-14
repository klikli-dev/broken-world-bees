package com.tao.brokenworldbees.genetics;

import binnie.extrabees.genetics.ExtraBeeBranchDefinition;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import binnie.extrabees.genetics.ExtraBeeSpecies;
import com.google.common.base.Preconditions;
import com.tao.brokenworldbees.BrokenWorldBees;
import com.tao.brokenworldbees.items.types.EnumHoneyComb;
import forestry.api.apiculture.*;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.Bee;
import forestry.apiculture.genetics.IBeeDefinition;
import forestry.core.genetics.IBranchDefinition;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;

public enum BrokenWorldBeeDefinition implements IBeeDefinition {

    //9dbbbb
    PROSPEROUS(ExtraBeeBranchDefinition.PRECIOUS, "prosperous", true, new Color(0x9dbbbb), new Color(0x9ababa)) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies
                    .addProduct(binnie.extrabees.items.types.EnumHoneyComb.STONE.get(1), 0.20f)
                    .addSpecialty(EnumHoneyComb.PROSPEROUS.get(1), 0.01f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.GOLD, ExtraBeeDefinition.DIAMOND, 2);
        }
    },
    URANIOUS(ExtraBeeBranchDefinition.NUCLEAR, "uranious", false, new Color(0x1eff00), new Color(0x999999)) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies
                    .addProduct(binnie.extrabees.items.types.EnumHoneyComb.BARREN.get(1), 0.20f)
                    .addSpecialty(EnumHoneyComb.URANIOUS.get(1), 0.02f)
                    .setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.NUCLEAR, ExtraBeeDefinition.GOLD, 5);
            registerMutation(ExtraBeeDefinition.NUCLEAR, ExtraBeeDefinition.SILVER, 5);
        }
    },;

    private final IBranchDefinition branch;
    private final ExtraBeeSpecies speciesBuilder;

    private IAlleleBeeSpecies species;
    private IAllele[] template;
    private IBeeGenome genome;

    BrokenWorldBeeDefinition(IBranchDefinition branch, String binomial, boolean dominant, Color primary, Color secondary) {
        String species = toString().toLowerCase(Locale.ENGLISH);
        String uid = BrokenWorldBees.MODID + ".species." + species;
        String description = BrokenWorldBees.MODID + ".species." + species + ".desc";
        String name = BrokenWorldBees.MODID + ".species." + species + ".name";

        this.branch = branch;
        if (branch != null) {
            this.speciesBuilder = new ExtraBeeSpecies(uid, name, "Tao", description, dominant, branch.getBranch(), binomial, primary.getRGB(), secondary.getRGB());
        } else {
            this.speciesBuilder = null;
        }
    }

    public static void doInit() {
        for (BrokenWorldBeeDefinition bee : values()) {
            if (bee.isNeedRegister()) {
                bee.init();
            }
        }

        for (BrokenWorldBeeDefinition bee : values()) {
            if (bee.isNeedRegister()) {
                bee.registerMutations();
            }
        }

    }

    public static IBeeMutationBuilder registerMutation(IBeeDefinition allele0, IBeeDefinition allele1, IBeeDefinition mutation, int chance) {
        return registerMutation(allele0.getGenome().getPrimary(), allele1.getGenome().getPrimary(), mutation.getTemplate(), chance);
    }

    public static IBeeMutationBuilder registerMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IAllele[] template, int chance) {
        Preconditions.checkNotNull(allele0);
        Preconditions.checkNotNull(allele1);
        Preconditions.checkNotNull(template);
        return BeeManager.beeMutationFactory.createMutation(allele0, allele1, template, chance);
    }

    protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
        // ignored
    }

    protected void setAlleles(IAllele[] template) {
        // ignored
    }

    protected void registerMutations() {
        // can be found in hive
    }

    protected boolean isNeedRegister() {
        return branch != null;
    }

    protected boolean isSecret() {
        return false;
    }

    private void init() {
        if (speciesBuilder == null) {
            return;
        }
        if (isSecret()) {
            speciesBuilder.setIsSecret();
        }
        setSpeciesProperties(speciesBuilder);
        species = speciesBuilder.build();

        template = branch.getTemplate();
        AlleleHelper.instance.set(template, EnumBeeChromosome.SPECIES, species);
        setAlleles(template);

        genome = BeeManager.beeRoot.templateAsGenome(template);
        BeeManager.beeRoot.registerTemplate(template);
    }

    public IBeeMutationBuilder registerMutation(IBeeDefinition allele0, IBeeDefinition allele1, int chance) {
        return registerMutation(allele0.getGenome().getPrimary(), allele1.getGenome().getPrimary(), getTemplate(), chance);
    }

    @Override
    public final IAllele[] getTemplate() {
        return Arrays.copyOf(template, template.length);
    }

    @Override
    public final IBeeGenome getGenome() {
        return genome;
    }

    @Override
    public final IBee getIndividual() {
        return new Bee(genome);
    }

    @Override
    public final ItemStack getMemberStack(EnumBeeType beeType) {
        IBee bee = getIndividual();
        return BeeManager.beeRoot.getMemberStack(bee, beeType);
    }

}
